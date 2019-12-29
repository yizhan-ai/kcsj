package domain;
import java.io.Serializable;

public class RoomPrice implements Comparable<RoomPrice>, Serializable {
    private Integer id;
    private String roomPrice;

    public RoomPrice(Integer id, String roomPrice) {
        this.id = id;
        this.roomPrice = roomPrice;
    }

    public RoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }


    @Override
    public int compareTo(RoomPrice o) {
        return this.id - o.id;
    }
}
