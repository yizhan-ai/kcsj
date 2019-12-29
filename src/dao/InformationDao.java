package dao;



import domain.*;
import domain.Information;
import service.CheckInService;
import service.RoomService;
import service.RoomStatusService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class InformationDao {
	private static InformationDao informationDao = new InformationDao();
	private InformationDao(){}
	public static InformationDao getInstance(){
		return informationDao;
	}

    public Collection<Information> findAll() throws SQLException{
        Collection<Information> informations = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建语句盒子对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象
        ResultSet resultSet = statement.executeQuery("select * from information");
        //若结果集仍然有下一条记录，则执行循环体
        while(resultSet.next()){
            Information information = new Information(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("idCard"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("inTime"),
                    resultSet.getString("outTime"),
                    RoomDao.getInstance().find(resultSet.getInt("room_id")));
            informations.add(information);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return informations;
    }


    public Collection<Information> findAllByManager(Integer manager_id)throws SQLException{
        //创建集合informations
        Collection<Information> informations = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上进行预编译
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Project where manager_id=?");
        //给参数赋值
        preparedStatement.setInt(1,manager_id);
        //执行SQL查询语句并获得结果集
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            Information information = new Information(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("idCard"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("inTime"),
                    resultSet.getString("outTime"),
                    RoomDao.getInstance().find(resultSet.getInt("room_id")));
          informations.add(information);
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return informations;
    }

    public Information find(Integer id) throws SQLException {
        Information information = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM information WHERE id=?");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            information = new Information(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("idCard"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("inTime"),
                    resultSet.getString("outTime"),
                    RoomDao.getInstance().find(resultSet.getInt("room_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return information;
    }
    public void add(Information information){
        //获得数据库连接对象
        //利用事务实现增加前台，就增加一个User记录
        //创建一个Connection对象
        //创建一个PreparedStatement对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        int informationId = 0;
        try {
            //执行getConn方法获得connection连接
            connection = JdbcHelper.getConn();
            //设置为手动提交
            connection.setAutoCommit(false);
            //在该连接上创建预编译语句对象，并获得自增型的id
            pstmt = connection.prepareStatement("INSERT INTO information( name,idCard,phoneNumber,inTime, outTime,room_id) VALUES"+" (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            //为参数赋值
            pstmt.setString(1,information.getName());
            pstmt.setString(2,information.getIdCard());
            pstmt.setString(3,information.getPhoneNumber());
            pstmt.setString(4,  information.getInTime());
            pstmt.setString(5, information.getOutTime());
            pstmt.setInt(6,information.getRoom().getId());
            //执行预编译语句
            int affectedRowNum = pstmt.executeUpdate();
            System.out.println("添加了 " + affectedRowNum + " 行记录");
            //获取自动生成的主键，并获得结果集
            ResultSet resultSet = pstmt.getGeneratedKeys();
            //若结果集仍有下一条记录，游标下移
            if(resultSet.next()) {
                //获得waiter的id并赋值给Waiter对象
                informationId = resultSet.getInt(1);
                information.setId(informationId);
            }


            //创建CheckIn对象
            CheckIn checkIn = new CheckIn(
                    information
            );
            //添加CheckIn对象
            CheckInService.getInstance().add(connection,checkIn);
            Room room = information.getRoom();
            room.setRoomStatus(RoomStatusService.getInstance().find(2));
            RoomService.getInstance().update(connection,room);
            System.out.println("ok");
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
    public void update(Information information) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement( " update information set name=?, idCard=?, phoneNumber=?, inTime=?, outTime=?, room_id=? where id=?");
        //为预编译参数赋值
        preparedStatement.setString(1,information.getName());
        preparedStatement.setString(2,information.getIdCard());
        preparedStatement.setString(3,information.getPhoneNumber());
        preparedStatement.setString(4,  information.getInTime());
        preparedStatement.setString(5,  information.getOutTime());
        preparedStatement.setInt(6,information.getRoom().getId());
        preparedStatement.setInt(7,information.getId());
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
            pstmt = connection.prepareStatement("DELETE FROM checkin WHERE information_id = ?");
            //为参数赋值
            pstmt.setInt(1, id);
            //执行预编译语句，删除相对应id的User记录
            pstmt.executeUpdate();

            //创建预编译语句
            pstmt = connection.prepareStatement("DELETE FROM information WHERE id = ?");
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

