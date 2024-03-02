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

    public String getName() {
        return name;
    }

    @NotNull
    @Column(unique = true)
    private String name;

    // "product" exists as property in target entity (ProductDetails)
    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ProductDetails> productDetails = new ArrayList<>();

}