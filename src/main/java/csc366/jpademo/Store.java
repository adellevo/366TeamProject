package csc366.jpademo;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long store_id;
    private Time opening_time;
    private Time closing_time;

    @OneToMany(mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<CustomerOrder> orders = new ArrayList<>();
}