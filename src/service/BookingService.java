package service;




import dao.BookingDao;
import domain.Booking;

import java.sql.SQLException;
import java.util.Collection;

public final class BookingService {
	private BookingDao bookingDao= BookingDao.getInstance();
	private static BookingService bookingService = new BookingService();

	private BookingService(){}

	public static BookingService getInstance(){
		return bookingService;
	}
	public Collection<Booking> findAll() throws SQLException {
		return bookingDao.findAll();
	}

	public Booking find(Integer id) throws SQLException {
		return bookingDao.find(id);
	}

	public void update(Booking booking) throws SQLException {
	    bookingDao.update(booking);
	}

	public void add(Booking booking) throws SQLException {
		bookingDao.add(booking);
	}

	public void delete(Integer id) throws SQLException {
		bookingDao.delete(id);
	}


	
}
