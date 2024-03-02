package csc366.jpademo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("2")
public class SalariedRole extends Role {
    @NotNull
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "SalariedRole{" +
                "salary=" + salary +
                '}';
    }
}
