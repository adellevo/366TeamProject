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

    public double getCost() {
        return total_cost;
    }

    public void setCost(double total_cost) {
        this.total_cost = total_cost;
    }

    @NotNull
    private double total_cost;

    @NotNull
    private int points_earned;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToMany
    @JoinTable(
            name = "order_product", // join table name
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    public Long getId() {
        return order_id;
    }

    public void setId(long id) {
        this.order_id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerOrder)) return false;
        return order_id != null && order_id.equals(((CustomerOrder) o).getId());
    }

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