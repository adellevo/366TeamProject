package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long>{
    @Query(value = "SELECT total_cost " +
                    "FROM customer_order " +
                    "WHERE customer_id = :customerId and order_id = :orderId", nativeQuery = true)
    Double findOrderTotalCost(@Param("customerId") long customer_id, @Param("orderId") long order_id);
}

