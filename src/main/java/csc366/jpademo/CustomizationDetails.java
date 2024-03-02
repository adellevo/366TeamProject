package csc366.jpademo;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CustomizationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customization_details_id;

    @MapsId("ingredientId")
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Customization customization;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private int quantity;

    @NotNull
    private int price;
}