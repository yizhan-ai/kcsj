package service;



import dao.RoomDao;
import domain.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public final class RoomService {
	private RoomDao roomDao= RoomDao.getInstance();
	private static RoomService roomService = new RoomService();

	private RoomService(){}

	public static RoomService getInstance(){
		return roomService;
	}
	public Collection<Room> findAll() throws SQLException {
		return roomDao.findAll();
	}

	public Room find(Integer id) throws SQLException {
		return roomDao.find(id);
	}

	public void update(Room room) throws SQLException {
		roomDao.update(room);
	}
	public void update(Connection connection,Room room) throws SQLException {
		roomDao.update(connection,room);
	}

	public void add(Room room) throws SQLException {
		roomDao.add(room);
	}

	public void delete(Integer id) throws SQLException {
		roomDao.delete(id);
	}


	
}
