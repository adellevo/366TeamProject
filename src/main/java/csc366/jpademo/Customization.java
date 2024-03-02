package csc366.jpademo;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customization extends Ingredient{
//    @OneToMany(mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.LAZY)
//    private List<Product> productsAddedTo = new ArrayList<>();
}
