package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    @Query("SELECT c FROM Customer c WHERE TYPE(c) != Member")
//    List<Customer> findNonMemberCustomers();
}

