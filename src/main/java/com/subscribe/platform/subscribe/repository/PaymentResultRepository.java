package com.subscribe.platform.subscribe.repository;

import com.subscribe.platform.subscribe.entity.PaymentResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentResultRepository extends JpaRepository<PaymentResult, Long> {
    Page<PaymentResult> findByOwnerIdAndCreatedDateBetween(@Param("customerId") Long customerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
