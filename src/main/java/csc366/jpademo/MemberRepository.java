package csc366.jpademo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    @Query(value = "SELECT loyalty_points FROM customer WHERE customer_id = :c_id", nativeQuery = true)
    Integer findLoyaltyPoints(@Param("c_id") long customer_id);
}

