package com.subscribe.platform.user.repository;

import com.subscribe.platform.user.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
