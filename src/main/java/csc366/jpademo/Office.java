package csc366.jpademo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Office {

    @Id
    private long office_id;

    @OneToOne(mappedBy = "office")
    @PrimaryKeyJoinColumn
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "office_employee",
            joinColumns = @JoinColumn(name = "office_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees = new ArrayList<>();

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
