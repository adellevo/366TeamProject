package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
    @Query("SELECT s FROM Store s " +
            "JOIN s.orders o " +
            "WHERE o.customer.id = :customerId")
    List<Store> findAllStoresByCustomerId(@Param("customerId") long customerId);
}
