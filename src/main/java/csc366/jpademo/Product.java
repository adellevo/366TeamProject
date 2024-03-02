package csc366.jpademo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_id;

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "products")
    private Set<CustomerOrder> orders;
}