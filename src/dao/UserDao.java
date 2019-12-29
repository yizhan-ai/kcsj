package dao;

import domain.User;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public final class UserDao {
    private static UserDao userDao=new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return userDao;
    }

    public User login(User user)throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user where username=? and password=?");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        User user1 = null;
        if (resultSet.next()) {
            user1 = UserDao.getInstance().find(resultSet.getInt("id"));
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user1;
    }

    public Collection<User> findByUsername(String username)throws SQLException{
        Set<User> users = new TreeSet<>();
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=?");
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            User user = UserDao.getInstance().find(resultSet.getInt("id"));
            users.add(user);
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return users;
    }

    public Collection<User> findAll()throws SQLException{
        Set<User> users= new TreeSet<User>();
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM user");
        ResultSet resultSet = psmt.executeQuery();
        while (resultSet.next()){
            User user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    ActorDao.getInstance().find(resultSet.getInt("actor_id")));
            users.add(user);
        }
        JdbcHelper.close(resultSet,psmt,connection);
        return users;
    }
    public boolean update(Connection connection,User user) throws SQLException {
        String updateDegree_sql = "UPDATE user SET username = ?,password = ?,actor_id = ? where id = ?";
        PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setInt(3,user.getActor().getId());
        pstmt.setInt(4,user.getId());
        int affectedRows=pstmt.executeUpdate();
        return affectedRows>0;
    }

    public boolean update(User user) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String updateDegree_sql = "UPDATE user SET username = ?,password = ?,actor_id = ? where id = ?";
        PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setInt(3,user.getActor().getId());
        pstmt.setInt(4,user.getId());
        int affectedRows=pstmt.executeUpdate();
        JdbcHelper.close(pstmt,connection);
        return affectedRows>0;
    }

    public void add(User user) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addUser_sql = "INSERT INTO user(username,password,actor_id) VALUES" +
                " (?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(addUser_sql);

        pstmt.setString(1, user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setInt(3,user.getActor().getId());

        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("user添加了 " + affectedRowNum +" 行记录");
        JdbcHelper.close(pstmt,connection);

    }
    public void add(Connection connection,User user) throws SQLException {
        String addUser_sql = "INSERT INTO user(username,password,actor_id) VALUES" +
                " (?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(addUser_sql);

        pstmt.setString(1, user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setInt(3,user.getActor().getId());

        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("添加了 " + affectedRowNum +" 行记录");

        pstmt.close();
    }

    public boolean delete(Integer id) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        pstmt.setInt(1,id);
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum +" 行记录");
        JdbcHelper.close(pstmt,connection);
        return affectedRowNum>0;
    }
    public User find(Integer id)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM user where id=?");
        psmt.setInt(1,id);
        ResultSet resultSet = psmt.executeQuery();
        User user = null;
        if (resultSet.next()){
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    ActorDao.getInstance().find(resultSet.getInt("actor_id")));

        }
        JdbcHelper.close(resultSet,psmt,connection);
        return user;
    }
}
