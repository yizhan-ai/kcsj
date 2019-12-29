package domain;

import java.io.Serializable;

public final class Role implements Comparable<Role>, Serializable {
    private Integer id;
    private String name;
    private String no;

    public Role(int id, String name, String no) {
        this.id = id;
        this.name = name;
        this.no = no;
    }

    public Role(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public int compareTo(Role o) {
        return this.id - o.id;
    }
}
