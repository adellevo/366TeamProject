package csc366.jpademo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class Role {
    @Id
    private long role_id;

    @NotNull
    private String role_name;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date effective_date;

    @NotNull
    private boolean exempt;

    @OneToMany(mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public Role() {}
    public Role(long role_id, String role_name, Date effective_date, boolean exempt) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.effective_date = effective_date;
        this.exempt = exempt;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Date getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(Date effective_date) {
        this.effective_date = effective_date;
    }

    public boolean isExempt() {
        return exempt;
    }

    public void setExempt(boolean is_exempt) {
        this.exempt = is_exempt;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                ", effective_date=" + effective_date +
                ", is_exempt=" + exempt +
                '}';
    }
}
