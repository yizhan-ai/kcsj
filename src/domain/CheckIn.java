package domain;

import java.io.Serializable;

public class CheckIn implements Comparable<CheckIn>, Serializable {
    private Integer id;
    private Information information;

    public CheckIn(Integer id, Information information) {
        this.id = id;
        this.information = information;
    }

    public CheckIn(Information information) {
        this.information = information;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    @Override
    public int compareTo(CheckIn o) {
        return this.id - o.id;
    }
}
