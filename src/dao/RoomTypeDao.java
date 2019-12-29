package dao;



import domain.RoomType;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class RoomTypeDao {
	private static RoomTypeDao roomTypeDao = new RoomTypeDao();
	
	private RoomTypeDao() {}
	public static RoomTypeDao getInstance(){
		return roomTypeDao;
	}


	public Collection<RoomType> findAll() throws SQLException {
		Collection<RoomType> roomTypes =new TreeSet<>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建Statement对象
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from roomtype");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//以当前记录中的id,description,no,remarks值为参数，创建Degree对象
			RoomType roomType  = new RoomType(resultSet.getInt("id"),
					resultSet.getString("name"),
					RoomPriceDao.getInstance().find(resultSet.getInt("roomPrice_id"))

					);
			//向roomTypes集合中添加RoomType对象
			roomTypes.add(roomType);
		}
		//关闭资源
		JdbcHelper.close(resultSet, statement, connection);
		return roomTypes;
	}

	public RoomType find(Integer id) throws SQLException {
		RoomType roomType = null;
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM roomtype WHERE id=?");
		//为第一个参数赋值
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//如果结果集仍有下一条记录，则游标下移
		if (resultSet.next()) {
			roomType = new RoomType(resultSet.getInt("id"),
					resultSet.getString("name"),
					RoomPriceDao.getInstance().find(resultSet.getInt("roomPrice_id"))
			);
		}
		//关闭资源
		JdbcHelper.close(resultSet, preparedStatement, connection);
		return roomType;
	}
	public void add(RoomType roomType)throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO roomtype (name, roomPrice_id) VALUES"+" (?,?)");
		//为预编译参数赋值
		preparedStatement.setString(1, roomType.getName());
		preparedStatement.setInt(2,roomType.getRoomPrice().getId());

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
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM roomtype WHERE id=?");
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("删除了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
	public void update(RoomType roomType)throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(" update roomtype set name=?,roomPrice_id=? where id=?");
		//为预编译参数赋值
		preparedStatement.setString(1,roomType.getName());
		preparedStatement.setInt(2,roomType.getRoomPrice().getId());
		preparedStatement.setInt(3,roomType.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("修改了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
	}
}

