package com.au.app.account.repository;

import com.au.app.account.domain.entity.PayeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayeeRepository extends JpaRepository<PayeeDetails, Integer> {
    @Query(value = "select * from payee_details WHERE customer_id = :customerId and is_active = true"
            + " ORDER BY created_at DESC ", nativeQuery = true)
    List<PayeeDetails> findByCustomerId(@Param("customerId") String customerId);

    Optional<PayeeDetails> findById(String id);
}



