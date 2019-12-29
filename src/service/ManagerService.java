package service;




import dao.ManagerDao;
import domain.Manager;

import java.sql.SQLException;
import java.util.Collection;

public final class ManagerService {
	private static ManagerDao managerDao= ManagerDao.getInstance();
	private static ManagerService managerService=new ManagerService();
	private ManagerService(){}
	
	public static ManagerService getInstance(){
		return managerService;
	}
	
	public Collection<Manager> findAll()throws SQLException{
		return managerDao.findAll();
	}
	
	public Manager find(Integer id)throws SQLException{
		return managerDao.find(id);
	}
	
	public void update(Manager manager)throws SQLException{
		managerDao.update(manager);
	}
	
	public void add(Manager manager)throws SQLException {
		managerDao.add(manager);
	}

	public void delete(Integer id)throws SQLException{
		managerDao.delete(id);
	}
}
