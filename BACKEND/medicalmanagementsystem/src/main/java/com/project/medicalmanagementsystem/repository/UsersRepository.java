package com.project.medicalmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.medicalmanagementsystem.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByUserName(String userName);

	Boolean existsByUserName(String userName);
}
