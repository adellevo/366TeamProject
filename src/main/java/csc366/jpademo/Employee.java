package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Employee {

    @Id
    private long employee_id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 15)
    @Column(unique = true)
    private String phone_number;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public Employee() {
    }

    public Employee(long employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", date=" + date +
                '}';
    }

}
