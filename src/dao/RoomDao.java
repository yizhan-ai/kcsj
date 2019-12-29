package dao;



import domain.Room;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class RoomDao {
	private static RoomDao roomDao = new RoomDao();
	private RoomDao(){}
	public static RoomDao getInstance(){
		return roomDao;
	}

    public Collection<Room> findAll() throws SQLException{
        Collection<Room> rooms = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建语句盒子对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象
        ResultSet resultSet = statement.executeQuery("select * from room");
        //若结果集仍然有下一条记录，则执行循环体
        while(resultSet.next()){
            Room room = new Room(resultSet.getInt("id"),
                    resultSet.getString("no"),
                    RoomTypeDao.getInstance().find(resultSet.getInt("roomType_id")),
                    RoomStatusDao.getInstance().find(resultSet.getInt("roomStatus_id")));
            rooms.add(room);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return rooms;
    }




    public Room find(Integer id) throws SQLException {
        Room room = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM room WHERE id=?");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            room = new Room(resultSet.getInt("id"),
                    resultSet.getString("no"),
                    RoomTypeDao.getInstance().find(resultSet.getInt("roomType_id")),
                    RoomStatusDao.getInstance().find(resultSet.getInt("roomStatus_id"))
                   );
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return room;
    }
    public void add(Room room) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO room(no,roomType_id,roomStatus_id) VALUES"+" (?,?,?)");
        //为预编译参数赋值
        preparedStatement.setString(1,room.getNo());
        preparedStatement.setInt(2,room.getRoomType().getId());
        preparedStatement.setInt(3,room.getRoomStatus().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }
    public void update(Connection connection,Room room) throws SQLException{

        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement( " update room set no=?,roomType_id=?,roomStatus_id=? where id=?");
        //为预编译参数赋值
        preparedStatement.setString(1,room.getNo());
        preparedStatement.setInt(2,room.getRoomType().getId());
        preparedStatement.setInt(3,room.getRoomStatus().getId());
        preparedStatement.setInt(4,room.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了"+affectedRows+"行记录");
        //关闭资源
    }
    public void update(Room room) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement( " update room set no=?,roomType_id=?,roomStatus_id=? where id=?");
        //为预编译参数赋值
        preparedStatement.setString(1,room.getNo());
        preparedStatement.setInt(2,room.getRoomType().getId());
        preparedStatement.setInt(3,room.getRoomStatus().getId());
        preparedStatement.setInt(4,room.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }

    public void delete(Integer id) throws SQLException {
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM room WHERE id=?");
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("删除了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }

}

