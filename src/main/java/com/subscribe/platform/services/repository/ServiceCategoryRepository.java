package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.Category;
import com.subscribe.platform.services.entity.ServiceCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

    @EntityGraph(attributePaths = {"category"})
    Set<ServiceCategory> findAllByServices_Id(Long serviceId);

    @EntityGraph(attributePaths = {"category"})
    Set<ServiceCategory> findByIdIn(List<Long> serviceCategoryId);

    void deleteByServices_Id(@Param("serviceId") Long ServiceId);
}
