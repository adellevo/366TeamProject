package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
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
    @Temporal(TemporalType.TIME)
    private Time start_time;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Time end_time;

    private boolean present;

    public Shift() {}

    public Shift(Date date, Time start_time, Time end_time, boolean present) {
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

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
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
