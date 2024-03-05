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

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

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

        Time openingTime1 = Time.valueOf(LocalTime.of(20, 0));
        Time closingTime1 = Time.valueOf(LocalTime.of(20, 0));

        Store store1 = new Store(openingTime1, closingTime1);
        entityManager.persist(store1);
        Store store2 = new Store(openingTime1, closingTime1);
        entityManager.persist(store2);

        Product product1 = new Product("coffee");
        entityManager.persist(product1);
        Product product2 = new Product("scone");
        entityManager.persist(product2);
        Product product3 = new Product("smoothie");
        entityManager.persist(product3);

        Date date1 = new GregorianCalendar(2024, Calendar.JANUARY, 2).getTime();
        Date date2 = new GregorianCalendar(2024, Calendar.FEBRUARY, 18).getTime();
        Date date3 = new GregorianCalendar(2024, Calendar.MAY, 19).getTime();

        //  ------- Customer 1 Setup -------

        Customer customer1 = new Customer();
        entityManager.persist(customer1);

        CustomerOrder order1 = new CustomerOrder(date1, customer1, store1);
        entityManager.persist(order1);

        customer1.setCustomerOrders(Arrays.asList(order1));

        ProductDetails productDetails1 = new ProductDetails(1, 6.50, "S", 10);
        productDetails1.setOrder(order1);
        productDetails1.setProduct(product1);
        order1.setCost(productDetails1.getPrice());
        entityManager.persist(productDetails1);

        //  ------- Member 1 Setup -------

        Member member1 = new Member("Jane", "Doe", "jdoe@gmail.com", "890-290-2988");
        entityManager.persist(member1);

        CustomerOrder order2 = new CustomerOrder(date2, member1, store1);
        entityManager.persist(order2);

        CustomerOrder order3 = new CustomerOrder(date3, member1, store1);
        entityManager.persist(order3);

        member1.setCustomerOrders(Arrays.asList(order2, order3));

        ProductDetails productDetails2 = new ProductDetails(4, 3.25, "L", 40);
        productDetails2.setOrder(order2);
        productDetails2.setProduct(product2);
        order2.setPoints(productDetails2.getQuantity(), productDetails2.getPointValue());
        order2.setCost(productDetails2.getPrice());
        member1.setLoyaltyPoints(member1.getLoyaltyPoints() + order2.getPoints());
        entityManager.persist(productDetails2);

        ProductDetails productDetails3 = new ProductDetails(2, 1.25, "S", 20);
        productDetails3.setOrder(order3);
        productDetails3.setProduct(product3);
        order3.setPoints(productDetails3.getQuantity(), productDetails3.getPointValue());
        order3.setCost(productDetails3.getPrice());
        member1.setLoyaltyPoints(member1.getLoyaltyPoints() + order3.getPoints());
        entityManager.persist(productDetails3);

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
        assertEquals(3.25, totalCost);
    }

    // 2: Find the current loyalty points of a specific customer.
    @Test
    @Order(2)
    public void testFindLoyaltyPoints() {
        Member member = memberRepository.findAll().get(0);
        int loyaltyPoints = memberRepository.findLoyaltyPoints(member.getId());

        log.info("Loyalty points: " + loyaltyPoints);
        assertEquals(200, loyaltyPoints);
    }

    // 3: List all the products (and their cost) of a specific order.
    @Test
    @Order(3)
    public void testGetOrderDetails() {
        Customer customer = customerRepository.findAll().get(1);
        CustomerOrder order = customer.getCustomerOrders().get(0);
        List<ProductDetails> productDetails = productDetailsRepository.findByOrderId(order.getId());

        log.info("All product details: " + productDetails.toString());
        assertEquals("scone", productDetails.get(0).getProduct().getName());
        assertEquals(3.25, productDetails.get(0).getPrice());
        assertEquals(1, productDetails.size());
    }

    // 4: Find the most frequently ordered product by a specific customer (by quantity).
    @Test
    @Order(4)
    public void testFindMostFrequentlyOrderedProduct() {
        entityManager.clear();
        Date date = new GregorianCalendar(2024, Calendar.FEBRUARY, 18).getTime();

        Time openingTime = Time.valueOf(LocalTime.of(20, 0));
        Time closingTime = Time.valueOf(LocalTime.of(20, 0));

        Store store = new Store(openingTime, closingTime);
        entityManager.persist(store);

        Product product1 = new Product("latte");
        entityManager.persist(product1);

        Product product2 = new Product("banana bread");
        entityManager.persist(product2);

        Member member = new Member("Jenny", "Doe", "jenny@gmail.com", "810-290-2988");
        entityManager.persist(member);

        // orders
        for (int i = 0; i < 3; i++) {
            CustomerOrder order = new CustomerOrder(date, member, store);
            entityManager.persist(order);

            ProductDetails productDetails = new ProductDetails(1, 7.50, "L", 10);
            productDetails.setOrder(order);
            productDetails.setProduct(product1); // Product 1 is ordered 3 times, with a quantity of 1
            order.setPoints(productDetails.getQuantity(), productDetails.getPointValue());
            order.setCost(productDetails.getPrice());
            member.setLoyaltyPoints(member.getLoyaltyPoints() + order.getPoints());
            entityManager.persist(productDetails);
        }

        CustomerOrder order = new CustomerOrder(date, member, store);
        entityManager.persist(order);

        ProductDetails productDetails = new ProductDetails(4, 3.25, "L", 10);
        productDetails.setOrder(order);
        productDetails.setProduct(product2); // Product 1 is ordered 3 times, with a quantity of 1
        order.setPoints(productDetails.getQuantity(), productDetails.getPointValue());
        order.setCost(productDetails.getPrice());
        member.setLoyaltyPoints(member.getLoyaltyPoints() + order.getPoints());
        entityManager.persist(productDetails);

        int PN_INDEX = 0;       // product name index
        int QTY_INDEX = 1;      // quantity index

        List<Object[]> allProductsOrdered = productDetailsRepository.findFavoriteProductByCustomerId(member.getId());
        Object[] mostFrequentlyOrdered = allProductsOrdered.get(0);
        Product mostFrequentlyOrderedProduct = (Product) mostFrequentlyOrdered[PN_INDEX];

        log.info("Most frequently ordered product: " + mostFrequentlyOrderedProduct.toString() + " | " +
                "Quantity: " + mostFrequentlyOrdered[QTY_INDEX]);
        assertEquals(product2.getId(), mostFrequentlyOrderedProduct.getId());
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
        assertEquals(2, stores.size());
    }
}
