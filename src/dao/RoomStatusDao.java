package dao;



import domain.RoomStatus;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class RoomStatusDao {
	private static RoomStatusDao roomStatusDao = new RoomStatusDao();

	private RoomStatusDao() {
	}

	public static RoomStatusDao getInstance() {
		return roomStatusDao;
	}

	public Collection<RoomStatus> findAll() throws SQLException {
		Collection<RoomStatus> graduateProjectCategories =new TreeSet<>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建Statement对象
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from roomstatus");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//以当前记录中的id,description,no,remarks值为参数，创建Degree对象
			RoomStatus roomStatus  = new RoomStatus(resultSet.getInt("id"),
					resultSet.getString("description"));
			//向graduateProjectCategories集合中添加RoomStatus对象
			graduateProjectCategories.add(roomStatus);
		}
		//关闭资源
		JdbcHelper.close(resultSet, statement, connection);
		return graduateProjectCategories;
	}
	public RoomStatus find(Integer id) throws SQLException {
		RoomStatus roomStatus = null;
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM roomstatus WHERE id=?");
		//为第一个参数赋值
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//如果结果集仍有下一条记录，则游标下移
		if (resultSet.next()) {
			roomStatus = new RoomStatus(resultSet.getInt("id"),
					resultSet.getString("description"));
		}
		//关闭资源
		JdbcHelper.close(resultSet, preparedStatement, connection);
		return roomStatus;
	}
	public void add(RoomStatus roomStatus)throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO roomstatus (description) VALUES"+" (?)");
		//为预编译参数赋值
		preparedStatement.setString(1,roomStatus.getDescription());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum=preparedStatement.executeUpdate();
		System.out.println("添加了"+affectedRowNum+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
	public void delete(Integer id) throws SQLException {
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM roomstatus WHERE id=?");
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("删除了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
	public void update(RoomStatus roomStatus)throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(" update roomstatus set description=? where id=?");
		//为预编译参数赋值
		preparedStatement.setString(1,roomStatus.getDescription());
		preparedStatement.setInt(2,roomStatus.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("修改了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
}
