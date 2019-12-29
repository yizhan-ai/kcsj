package dao;


import domain.Booking;
import domain.Client;
import domain.User;
import service.ClientService;
import service.UserService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class BookingDao {
	private static BookingDao bookingDao = new BookingDao();
	private BookingDao(){}
	public static BookingDao getInstance(){
		return bookingDao;
	}

    public Collection<Booking> findAll() throws SQLException{
        Collection<Booking> bookings = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建语句盒子对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象
        ResultSet resultSet = statement.executeQuery("select * from booking");
        //若结果集仍然有下一条记录，则执行循环体
        while(resultSet.next()){
            Booking booking = new Booking(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("idCard"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("inTime"),
                    resultSet.getString("outTime"),
                    RoomTypeDao.getInstance().find(resultSet.getInt("roomType_id"))
                    );
            bookings.add(booking);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return bookings;
    }




    public Booking find(Integer id) throws SQLException {
        Booking booking = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM booking WHERE id=?");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            booking = new Booking(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("idCard"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("inTime"),
                    resultSet.getString("outTime"),
                    RoomTypeDao.getInstance().find(resultSet.getInt("roomType_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return booking;
    }
    public void add(Booking booking) throws SQLException{
        //获得数据库连接对象
        //利用事务实现增加前台，就增加一个User记录
        //创建一个Connection对象
        //创建一个PreparedStatement对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        int bookingId = 0;
        try {
            //执行getConn方法获得connection连接
            connection = JdbcHelper.getConn();
            //设置为手动提交
            connection.setAutoCommit(false);
            //在该连接上创建预编译语句对象，并获得自增型的id
            pstmt = connection.prepareStatement("INSERT INTO booking( name,idCard,phoneNumber,inTime, outTime,roomType_id) VALUES"+" (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            //为参数赋值
            pstmt.setString(1, booking.getName());
            pstmt.setString(2, booking.getIdCard());
            pstmt.setString(3, booking.getPhoneNumber());
            pstmt.setString(4,  booking.getInTime());
            pstmt.setString(5,  booking.getOutTime());
            pstmt.setInt(6,booking.getRoomType().getId());
            //执行预编译语句
            int affectedRowNum = pstmt.executeUpdate();
            System.out.println("添加了 " + affectedRowNum + " 行记录");
            //获取自动生成的主键，并获得结果集
            ResultSet resultSet = pstmt.getGeneratedKeys();
            //若结果集仍有下一条记录，游标下移
            if(resultSet.next()) {
                //获得waiter的id并赋值给Waiter对象
                bookingId = resultSet.getInt(1);
                booking.setId(bookingId);
            }

//			//获得当前计算机的时间
//			java.util.Date date_util = new java.util.Date();
//			long date_long = date_util.getTime();
//			Date date_sql = new Date(date_long);


            //创建User对象
            Client client = new Client(
                    booking
            );
            //添加User对象
            ClientService.getInstance().add(connection,client);
            //提交
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
            try {
                //若connection不为空，回滚
                if (connection != null) {
                    System.out.println("回滚");
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e.printStackTrace();
            }

        } finally {
            try {
                if (connection != null) {
                    //恢复自动提交
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(pstmt, connection);
        }

    }

    public void update(Booking booking) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement( " update booking set name=?,idCard=?,phoneNumber=?,inTime=?, outTime=?,roomType_id=? where id=?");
        //为预编译参数赋值
        preparedStatement.setString(1,booking.getName());
        preparedStatement.setString(2,booking.getIdCard());
        preparedStatement.setString(3,booking.getPhoneNumber());
        preparedStatement.setString(4, booking.getInTime());
        preparedStatement.setString(5, booking.getOutTime());
        preparedStatement.setInt(6,booking.getRoomType().getId());
        preparedStatement.setInt(7,booking.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }

    public void delete(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            //获得connection连接
            connection = JdbcHelper.getConn();
            connection.setAutoCommit(false);
            //创建预编译语句
            pstmt = connection.prepareStatement("DELETE FROM client WHERE booking_id = ?");
            //为参数赋值
            pstmt.setInt(1, id);
            //执行预编译语句，删除相对应id的User记录
            pstmt.executeUpdate();

            //创建预编译语句
            pstmt = connection.prepareStatement("DELETE FROM booking WHERE id = ?");
            //为参数赋值
            pstmt.setInt(1, id);
            //执行预编译语句，删除相对应id的manager记录
            pstmt.executeUpdate();



            //提交
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e.printStackTrace();
            }

        } finally {
            try {
                if (connection != null) {
                    //恢复自动提交
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(pstmt, connection);
        }
    }
}

