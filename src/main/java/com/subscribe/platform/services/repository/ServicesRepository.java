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

    @Query("SELECT s FROM Services s JOIN ServiceImage si ON s.id = si.services.id WHERE s.store.id = :storeId AND si.imageType = 'THUMBNAIL' AND si.imageSeq = 1")
    Page<Services> findByJoinImage(Long storeId, Pageable pageable);


}
