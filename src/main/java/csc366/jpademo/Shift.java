package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.OffsetTime;
import java.util.Date;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shift_id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    private OffsetTime start_time;

    @NotNull
    private OffsetTime end_time;

    private boolean present;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Shift() {}

    public Shift(Date date, OffsetTime start_time, OffsetTime end_time, boolean present) {
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.present = present;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OffsetTime getStart_time() {
        return start_time;
    }

    public void setStart_time(OffsetTime start_time) {
        this.start_time = start_time;
    }

    public OffsetTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(OffsetTime end_time) {
        this.end_time = end_time;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "shift_id=" + shift_id +
                ", date=" + date +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", present=" + present +
                '}';
    }
}
