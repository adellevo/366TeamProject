package csc366.jpademo;

import org.assertj.core.api.Assertions;
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
import javax.validation.ConstraintViolationException;

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
public class RegionalManagerTests {

    private final static Logger log = LoggerFactory.getLogger(Demo2.class);

    @Autowired
    private EntityManager entityManager;  // https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionalManagerRepository regionalManagerRepository;


  

    @BeforeEach
    private void setup() {

        entityManager.clear();
        RegionalManager rm1 = new RegionalManager(1, "Spike", "Spiegel");
        entityManager.persist(rm1);
        Region r1 = new Region(1, "Mars", rm1);
        entityManager.persist(r1);
        entityManager.flush();
        
    }

    @Test
    @Order(1)
    public void testFindRegionalManagers() {
        List<RegionalManager> rms = regionalManagerRepository.findAll();
        log.info(rms.toString());
    }

    @Test
    @Order(2)
    public void testFindRegionalManagers2() {
        List<RegionalManager> rms = regionalManagerRepository.findAll();
        log.info(rms.toString());
    }

    @Test
    @Order(3)
    public void testSaveRegionalManagerWithNullLastName() {
        // Given
        RegionalManager regionalManagerBad = new RegionalManager(2, "Bob", null);
        try {
            entityManager.persist(regionalManagerBad);
            entityManager.flush(); // Flush to trigger validation
        } catch (ConstraintViolationException ex) {
            System.out.println("Fails no null last name constraint check");
            
            return; 
        }
 
    }

    @Test
    @Order(4)
    public void testSaveRegionalManagerWithNullFirstName() {
        // Given
        RegionalManager regionalManagerBad2 = new RegionalManager(3, null, "Dylan");
        try {
            entityManager.persist(regionalManagerBad2);
            entityManager.flush(); // Flush to trigger validation
        } catch (ConstraintViolationException ex) {
            System.out.println("Fails no null first name constraint check");
            
            return; 
        }
 
    }

  
    @Test
    @Order(5)
    public void testFindRegion() {
        List<Region> rs = regionRepository.findAll();
        log.info(rs.toString());
    }

    @Test
    @Order(6)
    public void testSaveRegionWithNullName() {
        RegionalManager rm1 = new RegionalManager(1, "Spike", "Spiegel");
        Region regionBad = new Region(2, null, rm1);
        try {
            entityManager.persist(regionBad);
            entityManager.flush(); // Flush to trigger validation
        } catch (ConstraintViolationException ex) {
            System.out.println("Fails no null region name constraint check");
            
            return; 
        }
 
    }
}