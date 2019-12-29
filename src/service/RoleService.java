package service;




import dao.RoleDao;
import domain.Role;

import java.sql.SQLException;
import java.util.Collection;

public final class RoleService {
	private static RoleDao roleDao= RoleDao.getInstance();
	private static RoleService roleService=new RoleService();
	private RoleService(){}
	
	public static RoleService getInstance(){
		return roleService;
	}
	
	public Collection<Role> findAll()throws SQLException{
		return roleDao.findAll();
	}
	
	public Role find(Integer id)throws SQLException{
		return roleDao.find(id);
	}
	
	public void update(Role role)throws SQLException{
		roleDao.update(role);
	}
	
	public void add(Role role)throws SQLException {
		roleDao.add(role);
	}

	public void delete(Integer id)throws SQLException{
		roleDao.delete(id);
	}
}
