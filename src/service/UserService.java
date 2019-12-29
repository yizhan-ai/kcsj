package service;




import dao.UserDao;
import domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;


public final class UserService {
	private UserDao userDao = UserDao.getInstance();
	private static UserService userService = new UserService();
	
	public UserService() {
	}
	
	public static UserService getInstance(){
		return UserService.userService;
	}


	public void add(User user)throws SQLException{
		userDao.add(user);
	}
	public void add(Connection collection, User user) throws SQLException {
		userDao.add(collection,user);
	}
	public boolean delete(Integer id)throws SQLException{
		return userDao.delete(id);
	}

	public boolean update(Connection connection,User user)throws SQLException{
		return userDao.update(connection,user);
	}
	public Collection<User> findAll()throws SQLException {
		return userDao.findAll();
	}
	public Collection<User> findByUsername(String username)throws SQLException {
		return userDao.findByUsername(username);
	}
	public User find(Integer id)throws SQLException{
		return userDao.find(id);
	}

	public User login(User user)throws SQLException{
		return userDao.login(user);
	}	
}
