package csc366.jpademo;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_details_id;

    @NotNull
    private int quantity;

    @NotNull
    private int price;

    @NotNull
    private int size;

    @NotNull
    private int point_value;

    public ProductDetails(int quantity, int price, int size, int point_value) {
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.point_value = point_value;
    }


    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPointValue() {
        return point_value;
    }

    public void setPointValue(int point_value) {
        this.point_value = point_value;
    }

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
