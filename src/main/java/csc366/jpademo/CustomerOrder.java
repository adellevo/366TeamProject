package csc366.jpademo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private double total_cost;

    private int points_earned;

    public CustomerOrder() {}

    public CustomerOrder(Date date, Customer customer, Store store) {
        this.date = date;
        this.customer = customer;
        this.store = store;
    }

    public int getPoints() {
        return points_earned;
    }

    public void setPoints(int quantity, int point_value) {
        this.points_earned = quantity * point_value;
    }

    public double getCost() {
        return total_cost;
    }

    public void setCost(double total_cost) {
        this.total_cost = total_cost;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Long getId() {
        return order_id;
    }

    public void setId(long id) {
        this.order_id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // "order" exists as property in target entity (ProductDetails)
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ProductDetails> productDetails = new ArrayList<>();

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "order_id=" + order_id +
                ", date=" + date +
                ", total_cost=" + total_cost +
                ", points_earned=" + points_earned +
                ", customer=" + customer.getId() +
                '}';
    }
}