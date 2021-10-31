package com.subscribe.platform.services.repository;

import com.subscribe.platform.services.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
