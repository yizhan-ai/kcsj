package dao;

import domain.Role;
import domain.UserRoleAss;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class UserRoleAssDao {
    private static UserRoleAssDao userRoleAssDao=new UserRoleAssDao();
    private UserRoleAssDao(){}
    public static UserRoleAssDao getInstance(){
        return userRoleAssDao;
    }

    public Collection<UserRoleAss> findAll()throws SQLException {
        Set<UserRoleAss> userRoleAsss= new TreeSet<UserRoleAss>();
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM userRoleAss");
        ResultSet resultSet = psmt.executeQuery();
        while (resultSet.next()){
            UserRoleAss userRoleAss = new UserRoleAss(resultSet.getInt("id"),
                    UserDao.getInstance().find(resultSet.getInt("user_id")),
                    RoleDao.getInstance().find(resultSet.getInt("role_id"))
            );
            userRoleAsss.add(userRoleAss);
        }
        JdbcHelper.close(resultSet,psmt,connection);
        return userRoleAsss;
    }

    public UserRoleAss findByUser(Integer userId)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM userRoleAss where user_id=?");
        psmt.setInt(1,userId);
        ResultSet resultSet = psmt.executeQuery();
        UserRoleAss userRoleAss = null;
        if (resultSet.next()){
            userRoleAss = new UserRoleAss(resultSet.getInt("id"),
                    UserDao.getInstance().find(resultSet.getInt("user_id")),
                    RoleDao.getInstance().find(resultSet.getInt("role_id"))
            );

        }
        JdbcHelper.close(resultSet,psmt,connection);
        return userRoleAss;
    }
    public UserRoleAss find(Integer id)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM userRoleAss where id=?");
        psmt.setInt(1,id);
        ResultSet resultSet = psmt.executeQuery();
        UserRoleAss userRoleAss = null;
        if (resultSet.next()){
            userRoleAss = new UserRoleAss(resultSet.getInt("id"),
                    UserDao.getInstance().find(resultSet.getInt("user_id")),
                    RoleDao.getInstance().find(resultSet.getInt("role_id"))
            );

        }
        JdbcHelper.close(resultSet,psmt,connection);
        return userRoleAss;
    }

    public boolean update(UserRoleAss userRoleAss) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String updateDegree_sql = "UPDATE userRoleAss SET user_id = ?,role_id = ? where id = ?";
        PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
        pstmt.setInt(1,userRoleAss.getUser().getId());
        pstmt.setInt(2,userRoleAss.getRole().getId());
        pstmt.setInt(3,userRoleAss.getId());
        int affectedRows=pstmt.executeUpdate();
        JdbcHelper.close(pstmt,connection);
        return affectedRows>0;
    }

    public void add(UserRoleAss userRoleAss) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addUserRoleAss_sql = "INSERT INTO userRoleAss(user_id,role_id) VALUES" +
                " (?,?)";
        PreparedStatement pstmt = connection.prepareStatement(addUserRoleAss_sql);
        pstmt.setInt(1,userRoleAss.getUser().getId());
        pstmt.setInt(2,userRoleAss.getRole().getId());

        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("userRoleAss添加了 " + affectedRowNum +" 行记录");
        JdbcHelper.close(pstmt,connection);

    }
    public boolean delete(Integer id) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM userRoleAss WHERE id = ?");
        pstmt.setInt(1,id);
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum +" 行记录");
        JdbcHelper.close(pstmt,connection);
        return affectedRowNum>0;
    }
}
