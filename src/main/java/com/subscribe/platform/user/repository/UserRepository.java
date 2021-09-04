package com.subscribe.platform.user.repository;

import com.subscribe.platform.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("select u.email from User u where u.email = :email")
    String findCheckByEmail(@Param("email") String email);

//    @EntityGraph(attributePaths = {"customer"})
//    User findByEmail(String email);
}
