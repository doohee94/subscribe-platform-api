package com.subscribe.platform.subscribe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.subscribe.platform.subscribe.entity.PickedOption;

public interface PickedOptionRepository extends JpaRepository<PickedOption, Long> {

	@EntityGraph(attributePaths = {"subscribe", "subscribe.customer"})
	List<PickedOption> findBySubscribe_Id(long subscribeId);

}
