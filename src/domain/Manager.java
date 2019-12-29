package domain;

import java.io.Serializable;

public class Manager extends Actor implements Comparable<Manager>, Serializable {
    public Manager(int id, String name, String no) {
        super(id, name, no);
    }

    public Manager(String name, String no) {
        super(name, no);
    }

    @Override
    public int compareTo(Manager o) {
        return this.id - o.id;
    }
}
