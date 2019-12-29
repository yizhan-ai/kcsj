package service;



import dao.RoomStatusDao;
import domain.RoomStatus;

import java.sql.SQLException;
import java.util.Collection;

public final class RoomStatusService {
	private static RoomStatusService roomStatusService = new RoomStatusService();
	private RoomStatusDao roomStatusDao = RoomStatusDao.getInstance();

	private RoomStatusService(){}
	
	public static RoomStatusService getInstance(){
		return roomStatusService;
	}


	public Collection<RoomStatus> findAll() throws SQLException {
		return roomStatusDao.findAll();
	}

	public RoomStatus find(Integer id) throws SQLException{
		return roomStatusDao.find(id);
	}

	public void update(RoomStatus roomStatus)throws SQLException {
		roomStatusDao.update(roomStatus);
	}

	public void add(RoomStatus roomStatus) throws SQLException {
		roomStatusDao.add(roomStatus);
	}

	public void delete(Integer id) throws SQLException {
		roomStatusDao.delete(id);
	}
}
