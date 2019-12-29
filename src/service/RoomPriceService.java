package service;




import dao.RoomPriceDao;
import domain.RoomPrice;

import java.sql.SQLException;
import java.util.Collection;

public final class RoomPriceService {
	private static RoomPriceService roomPriceService = new RoomPriceService();
	private RoomPriceDao roomPriceDao = RoomPriceDao.getInstance();

	private RoomPriceService(){}
	
	public static RoomPriceService getInstance(){
		return roomPriceService;
	}


	public Collection<RoomPrice> findAll() throws SQLException {
		return roomPriceDao.findAll();
	}

	public RoomPrice find(Integer id) throws SQLException{
		return roomPriceDao.find(id);
	}

	public void update(RoomPrice roomPrice)throws SQLException {
		roomPriceDao.update(roomPrice);
	}

	public void add(RoomPrice roomPrice) throws SQLException {
		roomPriceDao.add(roomPrice);
	}

	public void delete(Integer id) throws SQLException {
		roomPriceDao.delete(id);
	}
}
