package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIdIn(@Param("categoryIds") List<Long> categoryIds);
}
