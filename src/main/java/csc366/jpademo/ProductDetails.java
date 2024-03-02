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
    private double price;

    @NotNull
    private String size;

    @NotNull
    private int point_value;

    // price per unit
    public ProductDetails(int quantity, double price, String size, int point_value) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPointValue() {
        return point_value;
    }

    public void setPointValue(int point_value) {
        this.point_value = point_value;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "ProductDetails{" +
                ", quantity=" + quantity +
                ", price_per_unit=" + price +
                ", size=" + size +
                ", point_value=" + point_value +
                ", order=" + order +
                ", product=" + product.getName() +
                '}';
    }
}
