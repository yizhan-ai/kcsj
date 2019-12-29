package service;




import dao.UserRoleAssDao;
import domain.UserRoleAss;

import java.sql.SQLException;
import java.util.Collection;

public final class UserRoleAssService {
	private static UserRoleAssDao userRoleAssDao= UserRoleAssDao.getInstance();
	private static UserRoleAssService userRoleAssService=new UserRoleAssService();
	private UserRoleAssService(){}
	
	public static UserRoleAssService getInstance(){
		return userRoleAssService;
	}
	
	public Collection<UserRoleAss> findAll()throws SQLException{
		return userRoleAssDao.findAll();
	}
	
	public UserRoleAss find(Integer id)throws SQLException{
		return userRoleAssDao.find(id);
	}
	public UserRoleAss findByUser(Integer userId)throws SQLException{
		return userRoleAssDao.findByUser(userId);
	}
	public void update(UserRoleAss userRoleAss)throws SQLException{
		userRoleAssDao.update(userRoleAss);
	}
	
	public void add(UserRoleAss userRoleAss)throws SQLException {
		userRoleAssDao.add(userRoleAss);
	}

	public void delete(Integer id)throws SQLException{
		userRoleAssDao.delete(id);
	}
}
