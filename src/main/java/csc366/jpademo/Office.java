package csc366.jpademo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Office {

    @Id
    private long office_id;

    public Office() {}
    public Office(long office_id) {
        this.office_id = office_id;
    }

    @Override
    public String toString() {
        return "Office{" +
                "office_id=" + office_id +
                '}';
    }
}
