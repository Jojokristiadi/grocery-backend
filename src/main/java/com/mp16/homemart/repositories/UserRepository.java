package com.mp16.homemart.repositories;

import com.mp16.homemart.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
