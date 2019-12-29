package domain;

import java.io.Serializable;
import java.util.Date;

public class Information implements Comparable<Information>, Serializable {
    private Integer id;
    private String name;
    private String idCard;
    private String phoneNumber;
    private String inTime;
    private String outTime;
    private Room room;

    public Information(Integer id, String name, String idCard, String phoneNumber, String inTime, String outTime, Room room) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.inTime = inTime;
        this.outTime = outTime;
        this.room = room;
    }

    public Information(String name, String idCard, String phoneNumber, String inTime, String outTime, Room room) {
        this.name = name;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.inTime = inTime;
        this.outTime = outTime;
        this.room = room;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int compareTo(Information o) {
        return this.id - o.id;
    }
}
