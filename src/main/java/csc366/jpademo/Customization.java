package csc366.jpademo;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customization extends Ingredient{
    // “customization” exists as property in target entity (CustomizationDetails)
    @OneToMany(mappedBy = "customization",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<CustomizationDetails> customizationDetails = new ArrayList<>();
}
