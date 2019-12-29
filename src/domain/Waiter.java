package domain;

import java.io.Serializable;

public class Waiter extends Actor implements Comparable<Waiter>, Serializable {
    private String workdays;
    private String salary;

    public Waiter(int id, String name, String no, String workdays, String salary) {
        super(id, name, no);
        this.workdays = workdays;
        this.salary = salary;
    }

    public Waiter(String name, String no, String workdays, String salary) {
        super(name, no);
        this.workdays = workdays;
        this.salary = salary;
    }

    public String getWorkdays() {
        return workdays;
    }

    public void setWorkdays(String workdays) {
        this.workdays = workdays;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Waiter o) {
        return this.id - o.id;
    }
}
