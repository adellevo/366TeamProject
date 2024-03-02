package csc366.jpademo;
import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class NutritionLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long label_id;

    @NotNull
    @Column(unique = true)
    private String calories;

    @NotNull
    @Column(unique = true)
    private String total_grams_fat;
}