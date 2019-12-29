package domain;

public class Actor {
    public int id;
    public String name;
    public String no;

    public Actor(int id, String name, String no) {
        this.id = id;
        this.name = name;
        this.no = no;
    }

    public Actor(String name, String no) {
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
}
