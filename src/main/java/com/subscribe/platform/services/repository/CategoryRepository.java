package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
