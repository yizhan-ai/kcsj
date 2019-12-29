package domain;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Comparable<Booking>, Serializable {
    private Integer id;
    private String name;
    private String idCard;
    private String phoneNumber;
    private String inTime;
    private String outTime;
    private RoomType roomType;

    public Booking(Integer id, String name, String idCard, String phoneNumber, String inTime, String outTime, RoomType roomType) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.inTime = inTime;
        this.outTime = outTime;
        this.roomType = roomType;
    }

    public Booking(String name, String idCard, String phoneNumber, String inTime, String outTime, RoomType roomType) {
        this.name = name;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.inTime = inTime;
        this.outTime = outTime;
        this.roomType = roomType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }



    @Override
    public int compareTo(Booking o) {
        return this.id - o.id;
    }
}
