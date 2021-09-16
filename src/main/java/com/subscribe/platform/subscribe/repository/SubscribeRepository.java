package com.subscribe.platform.subscribe.repository;

import com.subscribe.platform.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    List<Subscribe> findByCustomerId(@Param("customerId") Long customerId);
}
