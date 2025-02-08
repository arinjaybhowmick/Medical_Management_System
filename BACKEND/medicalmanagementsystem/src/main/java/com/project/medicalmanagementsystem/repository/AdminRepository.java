package com.project.medicalmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.medicalmanagementsystem.model.Doctor;

public interface AdminRepository extends JpaRepository<Doctor, Long> {

}
