package domain;

import java.io.Serializable;

public class UserRoleAss implements Comparable<UserRoleAss>, Serializable {
    private int id;
    private User user;
    private Role role;

    public UserRoleAss(int id, User user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public UserRoleAss(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int compareTo(UserRoleAss o) {
        return this.id - o.id;
    }
}
