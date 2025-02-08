package com.project.medicalmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.medicalmanagementsystem.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRoleName(String roleName);

}
