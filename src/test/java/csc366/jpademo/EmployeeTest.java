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

import java.time.OffsetTime;
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
public class EmployeeTest {

    private final static Logger log = LoggerFactory.getLogger(Demo2.class);

    @Autowired
    private EntityManager entityManager;  // https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    private void setup() {
        Date date = new GregorianCalendar(2024, Calendar.FEBRUARY, 29).getTime();
        Employee e1 = new Employee(1, "John", "Doe", "john.doe@gmail.com", "000-000-0000", date);
        entityManager.persist(e1);
        Employee e2 = new Employee(2, "Jane", "Doe", "jane.doe@gmail.com", "000-000-0001", date);
        entityManager.persist(e2);

        HourlyRole r1 = new HourlyRole();
        r1.setRole_id(1);
        r1.setHourly_pay(25);
        r1.setRole_name("Server");
        r1.setEffective_date(date);

        SalariedRole r2 = new SalariedRole();
        r2.setRole_id(2);
        r2.setSalary(90000);
        r2.setRole_name("Supervisor");
        r2.setEffective_date(date);


        entityManager.persist(r1);
        entityManager.persist(r2);

        e1.setRole(r1);
        e2.setRole(r2);

        Address address1 = new Address("USA", "CA", "SLO", "1 Grand Ave", 93405);

        Store store1 = new Store();
        Office office1 = new Office();

        office1.setAddress(address1);

        office1.addEmployee(e1);

        store1.setAddress(address1);

        OffsetTime t1 = OffsetTime.now();
        OffsetTime t2 = OffsetTime.now();
        Shift shift1 = new Shift(date, t1, t2, false);

        shift1.setStore(store1);
        shift1.setEmployee(e2);




        entityManager.flush();  // "Synchronize the persistence context to the underlying database"
    }

    @Test
    @Order(1)
    public void testFindEmployee() {
        Employee e = employeeRepository.findByFirstName("John");
        log.info(e.toString());
    }


}
