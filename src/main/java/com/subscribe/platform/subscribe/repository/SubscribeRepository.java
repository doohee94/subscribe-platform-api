package com.subscribe.platform.subscribe.repository;

import com.subscribe.platform.subscribe.entity.Status;
import com.subscribe.platform.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    List<Subscribe> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") Status status);

    Optional<Subscribe> findByIdAndStatus(@Param("subscribeId") Long subscribeId, @Param("status") Status status);

    // 구독중인 서비스인지 확인 위해
//    Optional<Subscribe> findByCustomerIdAndServiceAndIdAndStatus(@Param("customerId") Long customerId, @Param("serviceId") Long serviceId, @Param("status") Status status);

    Optional<Subscribe> findByIdAndStatusAndCustomerId(@Param("subscribeId") Long subscribeId, @Param("status") Status status, @Param("customerId") Long customerId);

    @EntityGraph(attributePaths = {"services","services.store"})
    List<Subscribe> findByIdIn(@Param("subscribeIds") List<Long> subscribeIds);

    @EntityGraph(attributePaths = {"services"})
    @Query("SELECT subs, count(subs.services.id) as cnt FROM Subscribe subs WHERE subs.subscribeStartDate between :startDate AND :endDate GROUP BY subs.services.id order by cnt DESC")
    List<Subscribe> weeklyPopularity(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
