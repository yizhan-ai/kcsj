package service;




import dao.WaiterDao;
import domain.Waiter;

import java.sql.SQLException;
import java.util.Collection;

public final class WaiterService {
	private static WaiterDao waiterDao= WaiterDao.getInstance();
	private static WaiterService waiterService=new WaiterService();
	private WaiterService(){}
	
	public static WaiterService getInstance(){
		return waiterService;
	}
	
	public Collection<Waiter> findAll()throws SQLException{
		return waiterDao.findAll();
	}
	
	public Waiter find(Integer id)throws SQLException{
		return waiterDao.find(id);
	}

	public void update(Waiter waiter)throws SQLException{
		waiterDao.update(waiter);
	}
	
	public void add(Waiter waiter)throws SQLException {
		waiterDao.add(waiter);
	}

	public void delete(Integer id)throws SQLException{
		waiterDao.delete(id);
	}
}
