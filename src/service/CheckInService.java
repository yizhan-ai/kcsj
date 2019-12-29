package service;




import dao.CheckInDao;
import domain.CheckIn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class CheckInService {
	private CheckInDao checkInDao= CheckInDao.getInstance();
	private static CheckInService checkInService = new CheckInService();

	private CheckInService(){}

	public static CheckInService getInstance(){
		return checkInService;
	}
	public Collection<CheckIn> findAll() throws SQLException {
		return checkInDao.findAll();
	}

	public CheckIn find(Integer id) throws SQLException {
		return checkInDao.find(id);
	}

	public void update(CheckIn checkIn) throws SQLException {
	    checkInDao.update(checkIn);
	}

	public void add(CheckIn checkIn) throws SQLException {
		checkInDao.add(checkIn);
	}

	public void add(Connection connection,CheckIn checkIn) throws SQLException {
		checkInDao.add(connection,checkIn);
	}
	public void delete(Integer id) throws SQLException {
		checkInDao.delete(id);
	}


	
}
