package csc366.jpademo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
	"spring.main.banner-mode=off",
	"logging.level.root=ERROR",
	"logging.level.csc366=DEBUG",

        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:mysql://mysql.labthreesixsix.com/csc366",
        "spring.datasource.username=jpa",
        "spring.datasource.password=demo",
        
	"logging.level.org.hibernate.SQL=DEBUG",
	"logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE", // display prepared statement parameters
	"spring.jpa.properties.hibernate.format_sql=true",
	"spring.jpa.show-sql=false",   // prevent duplicate logging
	"spring.jpa.properties.hibernate.show_sql=false",	
	
	"logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n"
})
@ActiveProfiles("junit")
//@AutoConfigureTestDatabase(replace=Replace.NONE)   // without this, @DataJpaTest always uses in-memory H2 database
@TestMethodOrder(OrderAnnotation.class)
public class Demo2 {

    private final static Logger log = LoggerFactory.getLogger(Demo2.class);

    @Autowired
    private EntityManager entityManager;  // https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @BeforeEach
    private void setup() {
        entityManager.clear();  // "Clear the persistence context, causing all managed entities to become detached."

        Customer customer = new Customer(10);
        entityManager.persist(customer);

        CustomerOrder order = new CustomerOrder();
        order.setCost(35.00);
        order.setCustomer(customer);
        entityManager.persist(order);

        customer.setCustomerOrders(Arrays.asList(order));

        Product product = new Product("coffee");
        entityManager.persist(product);

        ProductDetails productDetails = new ProductDetails(1, 6.50, "S", 10);
        productDetails.setOrder(order);
        productDetails.setProduct(product);
        entityManager.persist(productDetails);

        Store store1 = new Store();
        CustomerOrder order2 = new CustomerOrder();
        order2.setCost(125.12);
        order2.setStore(store1);

        Store store2 = new Store();
        CustomerOrder order3 = new CustomerOrder();
        order3.setCost(9.10);
        order3.setStore(store2);

        Member member = new Member("Jane", "Doe", "jdoe@gmail.com", "8902902988");
        member.setCustomerOrders(Arrays.asList(order2, order3));

        entityManager.persist(store1);
        entityManager.persist(store2);
        entityManager.persist(member);
        entityManager.persist(order2);
        entityManager.persist(order3);

	    entityManager.flush();  // "Synchronize the persistence context to the underlying database"
    }

    // #1: Find the total amount spent by a specific customer on a specific order.
    @Test
    @Order(1)
    public void testFindOrderTotalCost() {
        Customer customer = customerRepository.findAll().get(1);
        CustomerOrder order = customer.getCustomerOrders().get(0);
        Double totalCost = orderRepository.findOrderTotalCost(customer.getId(), order.getId());

        log.info("Total cost: " + totalCost);
        assertEquals(35.00, totalCost);
    }

    // 2: Find the current loyalty points of a specific customer.
    @Test
    @Order(2)
    public void testFindLoyaltyPoints() {
        Member member = memberRepository.findAll().get(0);
        int loyaltyPoints = memberRepository.findLoyaltyPoints(member.getId());

        log.info("Loyalty points: " + loyaltyPoints);
        assertEquals(20, loyaltyPoints);
    }

    // 3: List all the products (and their cost) of a specific order.
    @Test
    @Order(3)
    public void testGetOrderDetails() {
        Customer customer = customerRepository.findAll().get(1);
        CustomerOrder order = customer.getCustomerOrders().get(0);
        List<ProductDetails> productDetails = productDetailsRepository.findByOrderId(order.getId());

        log.info("All product details: " + productDetails.toString());
        assertEquals(productDetails.get(0).getProduct().getName(), "coffee");
        assertEquals(productDetails.get(0).getPrice(), 6.50);
        assertEquals(1, productDetails.size());
    }

    // 4: Find the most frequently ordered product by a specific customer (by quantity).
    @Test
    @Order(4)
    public void testFindMostFrequentlyOrderedProduct() {
        entityManager.clear();
        Member member = memberRepository.findAll().get(0);

        // new products
        Product product1 = new Product("smoothie");
        entityManager.persist(product1);

        Product product2 = new Product("scone");
        entityManager.persist(product2);

        // orders
        for (int i = 0; i < 3; i++) {
            CustomerOrder order = new CustomerOrder();
            order.setCost(7.50);
            order.setCustomer(member);
            entityManager.persist(order);

            ProductDetails productDetails = new ProductDetails(1, 7.50, "L", 10);
            productDetails.setOrder(order);
            productDetails.setProduct(product1);  // Product 1 is ordered 3 times, with a quantity of 1
            entityManager.persist(productDetails);
        }

        CustomerOrder order = new CustomerOrder();
        order.setCost(100.00);
        order.setCustomer(member);
        entityManager.persist(order);

        ProductDetails productDetails = new ProductDetails(4, 3.25, "L", 10);
        productDetails.setOrder(order);
        productDetails.setProduct(product2);  // Product 2 is ordered once, with a quantity of 4
        entityManager.persist(productDetails);

        int PN_INDEX = 0;       // product name index
        int QTY_INDEX = 1;      // quantity index

        List<Object[]> allProductsOrdered = productDetailsRepository.findFavoriteProductByCustomerId(member.getId());
        Object[] mostFrequentlyOrdered = allProductsOrdered.get(0);
        Product mostFrequentlyOrderedProduct = (Product) mostFrequentlyOrdered[PN_INDEX];

        log.info("Most frequently ordered product: " + mostFrequentlyOrderedProduct.toString() + " | " +
                "Quantity: " + mostFrequentlyOrdered[QTY_INDEX]);
        assertEquals(product2.getId(), mostFrequentlyOrderedProduct.getId());

        assertEquals(allProductsOrdered.get(0)[QTY_INDEX], 4L);
        assertEquals(allProductsOrdered.get(1)[QTY_INDEX], 3L);
        entityManager.flush();
    }

    // 5: List all stores from which a specific customer has placed an order.
    @Test
    @Order(5)
    public void testFindAllStoresOrderedFrom() {
        Member member = memberRepository.findAll().get(0);
        long memberId = member.getId();
        System.out.println("memberId: " + memberId);
        List<Store> stores = storeRepository.findAll();

        log.info("All stores: " + stores);
        assertEquals(stores.size(), 2);
    }
}
