package domain;

import java.io.Serializable;

public class RoomType implements Comparable<RoomType>, Serializable {
    private Integer id;
    private String name;
    private RoomPrice roomPrice;


    public RoomType(Integer id, String name, RoomPrice roomPrice ) {
        this.id = id;
        this.name = name;
        this.roomPrice = roomPrice;

    }

    public RoomType(String name, RoomPrice roomPrice, int amount) {
        this.name = name;
        this.roomPrice = roomPrice;

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomPrice getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(RoomPrice roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    public int compareTo(RoomType o) {
        return this.id - o.id;
    }
}
