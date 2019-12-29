package dao;

import domain.Actor;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public class ActorDao {
    private static ActorDao actorDao = new ActorDao();

    private ActorDao() {
    }

    public static ActorDao getInstance() {
        return actorDao;
    }

    public Collection<Actor> findAll() throws SQLException {
        Collection<Actor> actors = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建Statement对象
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from actor");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            //以当前记录中的id,description,no,remarks值为参数，创建Degree对象
            Actor actor = new Actor(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no")
            );
            //向admins集合中添加Admin对象
            actors.add(actor);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        return actors;
    }

    public Actor find(Integer id) throws SQLException {
        Actor actor = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM actor WHERE id=?");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            actor = new Actor(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no")
            );
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return actor;
    }
}
