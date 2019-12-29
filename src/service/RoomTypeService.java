package service;




import dao.RoomTypeDao;
import domain.RoomType;

import java.sql.SQLException;
import java.util.Collection;

public final class RoomTypeService {
	private static RoomTypeService roomTypeService = new RoomTypeService();
	private RoomTypeDao roomTypeDao = RoomTypeDao.getInstance();

	private RoomTypeService(){}
	
	public static RoomTypeService getInstance(){
		return roomTypeService;
	}


	public Collection<RoomType> findAll() throws SQLException {
		return roomTypeDao.findAll();
	}

	public RoomType find(Integer id) throws SQLException{
		return roomTypeDao.find(id);
	}

	public void update(RoomType roomType)throws SQLException {
		roomTypeDao.update(roomType);
	}

	public void add(RoomType roomType) throws SQLException {
		roomTypeDao.add(roomType);
	}

	public void delete(Integer id) throws SQLException {
		roomTypeDao.delete(id);
	}
}
