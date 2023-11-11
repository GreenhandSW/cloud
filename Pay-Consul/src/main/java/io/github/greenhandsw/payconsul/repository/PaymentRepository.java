package io.github.greenhandsw.payconsul.repository;

import io.github.greenhandsw.core.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

//    Payment getPaymentsById(@Param("id") Long id);
}
