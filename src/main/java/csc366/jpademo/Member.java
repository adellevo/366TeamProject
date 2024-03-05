package csc366.jpademo;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Entity
@DiscriminatorValue("1")
public class Member extends Customer{
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

    private int loyalty_points;

    public Member() {}

    public Member(String first_name, String last_name, String email, String phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.loyalty_points = 0;
    }

    public int getLoyaltyPoints() {
        return loyalty_points;
    }

    public void setLoyaltyPoints(int loyalty_points) {
        this.loyalty_points = loyalty_points;
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

    @Override
    public String toString() {
        return "Member{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", loyalty_points=" + loyalty_points +
                ", customer_orders" + this.getCustomerOrders() +
                '}';
    }
}