package service;




import dao.LoginClientDao;
import domain.LoginClient;

import java.sql.SQLException;
import java.util.Collection;

public final class LoginClientService {
	private static LoginClientDao loginClientDao= LoginClientDao.getInstance();
	private static LoginClientService loginClientService=new LoginClientService();
	private LoginClientService(){}
	
	public static LoginClientService getInstance(){
		return loginClientService;
	}
	
	public Collection<LoginClient> findAll()throws SQLException{
		return loginClientDao.findAll();
	}
	
	public LoginClient find(Integer id)throws SQLException{
		return loginClientDao.find(id);
	}
	
	public void update(LoginClient loginClient)throws SQLException{
		loginClientDao.update(loginClient);
	}
	
	public void add(LoginClient loginClient)throws SQLException {
		loginClientDao.add(loginClient);
	}

	public void delete(Integer id)throws SQLException{
		loginClientDao.delete(id);
	}
}
