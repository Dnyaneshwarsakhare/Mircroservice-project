package com.dnyanu.user.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnyanu.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	
}
