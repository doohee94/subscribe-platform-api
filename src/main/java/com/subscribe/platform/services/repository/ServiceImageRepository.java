package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.ServiceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceImageRepository extends JpaRepository<ServiceImage, Long> {

    void deleteByServices_IdAndIdNotIn(@Param("serviceId") Long serviceId, @Param("imageIds") List<Long> imageIds);

    List<ServiceImage> findByServices_Id(@Param("serviceId") Long serviceId);
}
