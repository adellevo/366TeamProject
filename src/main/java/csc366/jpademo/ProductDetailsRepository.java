package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long>{
    @Query("SELECT pd.product, SUM(pd.quantity) FROM ProductDetails pd WHERE pd.order.customer.id = :customerId GROUP BY pd.product ORDER BY SUM(pd.quantity) DESC")
    List<Object[]> findFavoriteProductByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT pd FROM ProductDetails pd WHERE pd.order.order_id = :orderId")
    List<ProductDetails> findByOrderId(@Param("orderId") Long orderId);
}

