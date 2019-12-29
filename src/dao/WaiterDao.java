package dao;

import domain.Actor;
import domain.Waiter;
import domain.User;
import service.UserService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public class WaiterDao {
    private static WaiterDao waiterDao = new WaiterDao();

    private WaiterDao() {
    }

    public static WaiterDao getInstance() {
        return waiterDao;
    }

    public Collection<Waiter> findAll() throws SQLException {
        Collection<Waiter> waiters = new TreeSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建Statement对象
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from waiter,actor where actor.id = waiter.id");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            //以当前记录中的id,description,no,remarks值为参数，创建Degree对象
            Waiter waiter = new Waiter(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no"),
                    resultSet.getString("workdays"),
                    resultSet.getString("salary")
            );
            //向waiters集合中添加Waiter对象
            waiters.add(waiter);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        return waiters;
    }

    public Waiter find(Integer id) throws SQLException {
        Waiter waiter = null;
        Connection connection = JdbcHelper.getConn();
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM waiter,actor WHERE waiter.id=? and actor.id = waiter.id");
        //为第一个参数赋值
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //如果结果集仍有下一条记录，则游标下移
        if (resultSet.next()) {
            waiter = new Waiter(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no"),
                    resultSet.getString("workdays"),
                    resultSet.getString("salary")
            );
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return waiter;
    }

    public void add(Waiter waiter) {
        //利用事务实现增加前台，就增加一个User记录
        //创建一个Connection对象
        //创建一个PreparedStatement对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        int actorId = 0;
        try {
            //执行getConn方法获得connection连接
            connection = JdbcHelper.getConn();
            //设置为手动提交
            connection.setAutoCommit(false);
            //在该连接上创建预编译语句对象，并获得自增型的id
            pstmt = connection.prepareStatement("INSERT INTO actor(name, no) VALUES" +
                    " (?,?)", Statement.RETURN_GENERATED_KEYS);
            //为参数赋值
            pstmt.setString(1, waiter.getName());
            pstmt.setString(2, waiter.getNo());
            //执行预编译语句
            int affectedRowNum = pstmt.executeUpdate();
            System.out.println("添加了 " + affectedRowNum + " 行记录");
            //获取自动生成的主键，并获得结果集
            ResultSet resultSet = pstmt.getGeneratedKeys();
            //若结果集仍有下一条记录，游标下移
            if(resultSet.next()) {
                //获得waiter的id并赋值给Waiter对象
                actorId = resultSet.getInt(1);
                waiter.setId(actorId);
            }
            pstmt = connection.prepareStatement("INSERT INTO waiter(id,workdays,salary) VALUES" +
                    " (?,?,?)");
            pstmt.setInt(1,actorId);
            pstmt.setString(2,waiter.getWorkdays());
            pstmt.setString(3,waiter.getSalary());
            pstmt.executeUpdate();

//			//获得当前计算机的时间
//			java.util.Date date_util = new java.util.Date();
//			long date_long = date_util.getTime();
//			Date date_sql = new Date(date_long);


            //创建User对象
            User user = new User(
                    waiter.getName(),
                    waiter.getNo(),
                    ActorDao.getInstance().find(actorId)
            );
            //添加User对象
            UserService.getInstance().add(connection,user);
            //提交
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
            try {
                //若connection不为空，回滚
                if (connection != null) {
                    System.out.println("回滚");
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



    public void delete(Integer id) {
        //利用事务实现删除教师，就删除对应的User记录
        //创建一个Connection对象
        //创建一个PreparedStatement对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            //获得connection连接
            connection = JdbcHelper.getConn();
            connection.setAutoCommit(false);
            //创建预编译语句
            pstmt = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            //为参数赋值
            pstmt.setInt(1,id);
            //执行预编译语句，删除相对应id的User记录
            pstmt.executeUpdate();

            //创建预编译语句
            pstmt = connection.prepareStatement("DELETE FROM waiter WHERE id = ?");
            //为参数赋值
            pstmt.setInt(1,id);
            //执行预编译语句，删除相对应id的waiter记录
            pstmt.executeUpdate();

            //创建预编译语句
            pstmt = connection.prepareStatement("DELETE FROM actor WHERE id = ?");
            //为参数赋值
            pstmt.setInt(1,id);
            //执行预编译语句，删除相对应id的waiter记录
            pstmt.executeUpdate();


            //提交
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
            try {
                if (connection != null){
                    connection.rollback();
                }
            } catch (SQLException e1){
                e.printStackTrace();
            }

        } finally {
            try {
                if (connection != null){
                    //恢复自动提交
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(pstmt,connection);
        }
    }

    public void update(Waiter waiter) throws SQLException{
        //利用事务实现增加前台，就增加一个User记录
        //创建一个Connection对象
        //创建一个PreparedStatement对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            //执行getConn方法获得connection连接
            connection = JdbcHelper.getConn();
            //设置为手动提交
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE actor,waiter SET actor.no=?,actor.name=?,waiter.workdays=?,waiter.salary=? where actor.id = ? and waiter.id = ?");
            //为参数赋值
            preparedStatement.setString(1,waiter.getNo());
            preparedStatement.setString(2,waiter.getName());
            preparedStatement.setString(3,waiter.getWorkdays());
            preparedStatement.setString(4,waiter.getSalary());
            preparedStatement.setInt(5,waiter.getId());
            preparedStatement.setInt(6,waiter.getId());

            Actor actor = new Actor(waiter.getId(),waiter.getName(),waiter.no);
            //创建User对象
            User user = new User(
                    waiter.getId(),
                    waiter.getName(),
                    waiter.getNo(),
                    actor
            );
            //添加User对象
            UserService.getInstance().update(connection,user);

            //提交
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nerrorCode = " + e.getErrorCode());
            try {
                //若connection不为空，回滚
                if (connection != null) {
                    System.out.println("回滚");
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
