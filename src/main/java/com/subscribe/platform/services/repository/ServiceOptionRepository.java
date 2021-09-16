package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.ServiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServiceOptionRepository extends JpaRepository<ServiceOption, Long> {

    @Transactional
    void deleteByServices_IdAndIdNotIn(@Param("servicesId") Long servicesId, @Param("optionIds") List<Long> optionIds);

    List<ServiceOption> findByIdIn(@Param("options") Long[] options);
}
