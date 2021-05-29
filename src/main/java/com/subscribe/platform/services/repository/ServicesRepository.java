package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Service, Long> {
}
