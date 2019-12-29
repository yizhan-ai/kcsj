package domain;


import java.io.Serializable;

public final class Client implements Comparable<Client>,Serializable {

	private Integer id;//对应着数据库表中的非业务主键 object id
	private Booking booking;

	public Client(Integer id, Booking booking) {
		this.id = id;
		this.booking = booking;
	}

	public Client(Booking booking) {
		this.booking = booking;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@Override
	public int compareTo(Client o) {
		return this.id - o.id;
	}
}
