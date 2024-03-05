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
    private int calories;

    @NotNull
    @Column(unique = true)
    private int total_grams_fat;

    public NutritionLabel( int calories, int total_grams_fat) {
        this.calories = calories;
        this.total_grams_fat = total_grams_fat;
    }

    @Override
    public String toString() {
        return "NutritionLabel{" +
                "label_id=" + label_id +
                ", calories=" + calories +
                ", total_grams_fat=" + total_grams_fat +
                '}';
    }
}