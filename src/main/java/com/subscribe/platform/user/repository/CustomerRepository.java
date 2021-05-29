package com.subscribe.platform.user.repository;

import com.subscribe.platform.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
