package domain;

import java.io.Serializable;

public class LoginClient extends Actor implements Comparable<LoginClient>, Serializable {
    public LoginClient(int id, String name, String no) {
        super(id, name, no);
    }

    public LoginClient(String name, String no) {
        super(name, no);
    }

    @Override
    public int compareTo(LoginClient o) {
        return this.id - o.id;
    }
}
