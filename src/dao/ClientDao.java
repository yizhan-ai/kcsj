package dao;



import domain.Booking;
import domain.Client;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class ClientDao {
	private static ClientDao clientDao = new ClientDao();
	private ClientDao(){}
	public static ClientDao getInstance(){
		return clientDao;
	}

    public Collection<Client> findAll() throws SQLException{
        Collection<Client> clients = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建语句盒子对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象
        ResultSet resultSet = statement.executeQuery("select * from client");
        //若结果集仍然有下一条记录，则执行循环体
        while(resultSet.next()){
            Client client = new Client(resultSet.getInt("id"),
                    BookingDao.getInstance().find(resultSet.getInt("booking_id")));
            clients.add(client);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return clients;
    }



    public Client find(Integer id) throws SQLException {
        Client client = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client WHERE id=?");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            client = new Client(resultSet.getInt("id"),
                    BookingDao.getInstance().find(resultSet.getInt("booking_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return client;
    }

    public void add(Client client) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client(booking_id) VALUES"+" (?)");
        //为预编译参数赋值
        preparedStatement.setInt(1,client.getBooking().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }
    public void add(Connection connection,Client client) throws SQLException{
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client(booking_id) VALUES"+" (?)");
        //为预编译参数赋值
        preparedStatement.setInt(1,client.getBooking().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");

    }
    public void update(Client client) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement( " update client set booking_id=? where id=?");
        //为预编译参数赋值
        preparedStatement.setInt(1,client.getBooking().getId());
        preparedStatement.setInt(2,client.getId());
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
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM client WHERE id=?");
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("删除了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
    }

}

