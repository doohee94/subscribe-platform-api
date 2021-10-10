package com.subscribe.platform.subscribe.repository;

import com.subscribe.platform.subscribe.entity.Status;
import com.subscribe.platform.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    List<Subscribe> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") Status status);

    Optional<Subscribe> findByIdAndStatus(@Param("subscribeId") Long subscribeId, @Param("status") Status status);

    // 구독중인 서비스인지 확인 위해
//    Optional<Subscribe> findByCustomerIdAndServiceAndIdAndStatus(@Param("customerId") Long customerId, @Param("serviceId") Long serviceId, @Param("status") Status status);

    Optional<Subscribe> findByIdAndStatusAndCustomerId(@Param("subscribeId") Long subscribeId, @Param("status") Status status, @Param("customerId") Long customerId);

    List<Subscribe> findByIdIn(@Param("subscribeIds") List<Long> subscribeIds);
}
