package com.subscribe.platform.user.repository;

import com.subscribe.platform.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
