package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class AddressInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long address_instance_id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @OneToMany(mappedBy = "addressInstance",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    public AddressInstance() {}

    public AddressInstance(Date start_date, Date end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "AddressInstance{" +
                "start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
