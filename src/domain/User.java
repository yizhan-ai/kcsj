package domain;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {
    private Integer id;
    private String username;
    private String password;
    private Actor actor;

    public User(int id, String username, String password, Actor actor) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.actor = actor;
    }

    public User(String username, String password, Actor actor) {
        this.username = username;
        this.password = password;
        this.actor = actor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public int compareTo(User o) {
        return this.id - o.id;
    }
}
