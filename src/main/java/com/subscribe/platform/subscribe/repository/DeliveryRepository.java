package com.subscribe.platform.subscribe.repository;

import java.time.LocalDate;
import java.util.List;

import com.subscribe.platform.subscribe.entity.Delivery;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	List<Delivery> findByStore_Id(long storeId);

	@EntityGraph(attributePaths = {"subscribe","subscribe.services"})
	List<Delivery> findByStoreIdAndDeliveryDate(long storeId, LocalDate date);

	@EntityGraph(attributePaths = {"subscribe","subscribe.services"})
	List<Delivery> findByStoreIdAndDeliveryDateBetween(long storeId, LocalDate startDay, LocalDate endDay);
}
