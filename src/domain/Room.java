package domain;

import java.io.Serializable;

public class Room implements Comparable<Room>, Serializable {
    private Integer id;
    private String no;
    private RoomType roomType;
    private RoomStatus roomStatus;

    public Room(Integer id, String no, RoomType roomType, RoomStatus roomStatus) {
        this.id = id;
        this.no = no;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
    }

    public Room(String no, RoomType roomType, RoomStatus roomStatus) {
        this.no = no;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public int compareTo(Room o) {
        return this.id - o.id;
    }
}
