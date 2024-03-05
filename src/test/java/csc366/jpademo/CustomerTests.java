package csc366.jpademo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.*;

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
public class CustomerTests {

    private final static Logger log = LoggerFactory.getLogger(Demo2.class);

    @Autowired
    private EntityManager entityManager;  // https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    private void setup() {
        entityManager.clear();  // "Clear the persistence context, causing all managed entities to become detached."

        Time openingTime1 = Time.valueOf(LocalTime.of(20, 0));
        Time closingTime1 = Time.valueOf(LocalTime.of(20, 0));

        Store store1 = new Store(openingTime1, closingTime1);
        entityManager.persist(store1);

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

        ProductDetails productDetails1 = new ProductDetails(4, 3.25, "L", 0);
        productDetails1.setOrder(order1);
        productDetails1.setProduct(product1);
        order1.setCost(productDetails1.getPrice());
        entityManager.persist(productDetails1);

        //   ------- Member 1 Setup -------

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

        //   ------- Member 2 Setup -------

        Member member2 = new Member("Johnny", "Doe", "johnny@gmail.com", "858-690-2300");
        entityManager.persist(member2);

        CustomerOrder order4 = new CustomerOrder(date2, member1, store1);
        entityManager.persist(order2);

        member1.setCustomerOrders(Arrays.asList(order4));

        ProductDetails productDetails4 = new ProductDetails(1, 2.25, "M", 30);
        productDetails4.setOrder(order4);
        productDetails4.setProduct(product2);
        order4.setPoints(productDetails4.getQuantity(), productDetails4.getPointValue());
        order2.setCost(productDetails4.getPrice());
        member1.setLoyaltyPoints(member1.getLoyaltyPoints() + order4.getPoints());
        entityManager.persist(productDetails4);

        entityManager.flush();  // "Synchronize the persistence context to the underlying database"
    }

    @Test
    @Order(1)
    public void testFindCustomers() {
        List<Customer> customers = customerRepository.findAll();
        log.info(customers.toString());
    }

    @Test
    @Order(2)
    public void testFindMembers() {
        List<Member> members = memberRepository.findAll();
        log.info(members.toString());
    }

    @Test
    @Order(3)
    public void testFindOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();
        log.info(orders.toString());
    }

    @Test
    @Order(4)
    public void testIngredientEntityCreation() {
        Ingredient ingredient = new Ingredient("sugar");
        log.info(ingredient.toString());
    }

    @Test
    @Order(5)
    public void testNutritionLabelEntityCreation() {
        int calories = 120;
        int total_grams_fat = 40;
        NutritionLabel nutritionLabel = new NutritionLabel(calories, total_grams_fat);
        log.info(nutritionLabel.toString());
    }

}
