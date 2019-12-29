package dao;



import domain.CheckIn;
import domain.Room;
import service.CheckInService;
import service.RoomService;
import service.RoomStatusService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class CheckInDao {
	private static CheckInDao checkInDao = new CheckInDao();
	private CheckInDao(){}
	public static CheckInDao getInstance(){
		return checkInDao;
	}

    public Collection<CheckIn> findAll() throws SQLException{
        Collection<CheckIn> checkIns = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建语句盒子对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象
        ResultSet resultSet = statement.executeQuery("select * from checkIn");
        //若结果集仍然有下一条记录，则执行循环体
        while(resultSet.next()){
            CheckIn checkIn = new CheckIn(resultSet.getInt("id"),
                    InformationDao.getInstance().find(resultSet.getInt("information_id")));
            checkIns.add(checkIn);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return checkIns;
    }



    public CheckIn find(Integer id) throws SQLException {
        CheckIn checkIn = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM checkIn WHERE id=?");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            checkIn = new CheckIn(resultSet.getInt("id"),
                    InformationDao.getInstance().find(resultSet.getInt("information_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return checkIn;
    }

    public void add(Connection connection,CheckIn checkIn) throws SQLException{
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checkIn(information_id) VALUES"+" (?)");
        //为预编译参数赋值
        preparedStatement.setInt(1,checkIn.getInformation().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");

    }

    public void add(CheckIn checkIn) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checkIn(information_id) VALUES"+" (?)");
        //为预编译参数赋值
        preparedStatement.setInt(1,checkIn.getInformation().getId());

        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }

    public void update(CheckIn checkIn) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement( " update checkIn set information_id=? where id=?");
        //为预编译参数赋值
        preparedStatement.setInt(1,checkIn.getInformation().getId());
        preparedStatement.setInt(2,checkIn.getId());
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
            //在该连接上创建预编译语句对象
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM checkIn WHERE id=?");
            //为预编译参数赋值
            preparedStatement.setInt(1,id);
            //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("删除了"+affectedRows+"行记录");
            CheckIn checkIn = CheckInService.getInstance().find(id);
            Room room = checkIn.getInformation().getRoom();
            room.setRoomStatus(RoomStatusService.getInstance().find(1));
            RoomService.getInstance().update(connection,room);
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

