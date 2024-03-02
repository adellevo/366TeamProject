package csc366.jpademo;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="customer_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class Customer {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customer_id;

    public Customer() {
        this.customer_id = customer_id;
    }

    public Customer(long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getId() {
        return customer_id;
    }

    public void setId(Long id) {
        this.customer_id = id;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,      
            fetch = FetchType.LAZY)
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", customerOrders=" + customerOrders +
                '}';
    }
}
