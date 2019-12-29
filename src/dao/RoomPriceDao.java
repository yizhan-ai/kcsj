package dao;


import domain.RoomPrice;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class RoomPriceDao {
	private static RoomPriceDao roomPriceDao = new RoomPriceDao();
	
	private RoomPriceDao() {}
	public static RoomPriceDao getInstance(){
		return roomPriceDao;
	}


	public Collection<RoomPrice> findAll() throws SQLException {
		Collection<RoomPrice> roomPrices =new TreeSet<>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建Statement对象
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from roomprice");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//以当前记录中的id,description,no,remarks值为参数，创建Degree对象
			RoomPrice roomPrice  = new RoomPrice(resultSet.getInt("id"),
					resultSet.getString("roomPrice"));
			//向roomPrices集合中添加RoomPrice对象
			roomPrices.add(roomPrice);
		}
		//关闭资源
		JdbcHelper.close(resultSet, statement, connection);
		return roomPrices;
	}
	public RoomPrice find(Integer id) throws SQLException {
		RoomPrice roomPrice = null;
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM roomprice WHERE id=?");
		//为第一个参数赋值
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//如果结果集仍有下一条记录，则游标下移
		if (resultSet.next()) {
			roomPrice = new RoomPrice(resultSet.getInt("id"),
					resultSet.getString("roomPrice"));
		}
		//关闭资源
		JdbcHelper.close(resultSet, preparedStatement, connection);
		return roomPrice;
	}
	public void add(RoomPrice roomPrice)throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO roomprice (roomPrice) VALUES"+" (?)");
		//为预编译参数赋值
		preparedStatement.setString(1, roomPrice.getRoomPrice());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
	public void delete(Integer id) throws SQLException {
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM roomprice WHERE id=?");
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
	public void update(RoomPrice roomPrice)throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(" update roompricee set description=? where id=?");
		//为预编译参数赋值
		preparedStatement.setString(1,roomPrice.getRoomPrice());
		preparedStatement.setInt(2,roomPrice.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
}

