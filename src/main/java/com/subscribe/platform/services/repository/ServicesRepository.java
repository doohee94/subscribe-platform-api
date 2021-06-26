package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.ImageType;
import com.subscribe.platform.services.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {

    @EntityGraph(attributePaths = {"serviceImages"})
    Page<Services> findByStore_Id(Long storeId, Pageable pageable);


}
