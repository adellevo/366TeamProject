package csc366.jpademo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("1")
public class HourlyRole extends Role {
    @NotNull
    private double hourly_pay;

    public double getHourly_pay() {
        return hourly_pay;
    }

    public void setHourly_pay(double hourly_pay) {
        this.hourly_pay = hourly_pay;
    }

    @Override
    public String toString() {
        return "HourlyRole{" +
                "hourly_pay=" + hourly_pay +
                '}';
    }
}
