package dao;

import domain.Role;
import domain.Role;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public class RoleDao {
    private static RoleDao roleDao=new RoleDao();
    private RoleDao(){}
    public static RoleDao getInstance(){
        return roleDao;
    }
    
    public Collection<Role> findAll()throws SQLException {
        Collection<Role> roles= new TreeSet<>();
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM role");
        ResultSet resultSet = psmt.executeQuery();

        while (resultSet.next()){
            Role role = new Role(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no")
                    );
            roles.add(role);
            System.out.println("m");
        }
        JdbcHelper.close(resultSet,psmt,connection);
        return roles;

    }


    public Role find(Integer id)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement psmt = connection.prepareStatement("SELECT * FROM role where id=?");
        psmt.setInt(1,id);
        ResultSet resultSet = psmt.executeQuery();
        Role role = null;
        if (resultSet.next()){
            role = new Role(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no")
            );

        }
        JdbcHelper.close(resultSet,psmt,connection);
        return role;
    }

    public boolean update(Role role) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String updateDegree_sql = "UPDATE role SET name = ?,no = ? where id = ?";
        PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
        pstmt.setString(1,role.getName());
        pstmt.setString(2,role.getNo());
        pstmt.setInt(3,role.getId());
        int affectedRows=pstmt.executeUpdate();
        JdbcHelper.close(pstmt,connection);
        return affectedRows>0;
    }

    public void add(Role role) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addRole_sql = "INSERT INTO role(name,no) VALUES" +
                " (?,?)";
        PreparedStatement pstmt = connection.prepareStatement(addRole_sql);

        pstmt.setString(1,role.getName());
        pstmt.setString(2,role.getNo());

        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("role添加了 " + affectedRowNum +" 行记录");
        JdbcHelper.close(pstmt,connection);

    }
    public boolean delete(Integer id) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM role WHERE id = ?");
        pstmt.setInt(1,id);
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum +" 行记录");
        JdbcHelper.close(pstmt,connection);
        return affectedRowNum>0;
    }
}
