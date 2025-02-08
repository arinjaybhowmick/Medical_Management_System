package com.project.medicalmanagementsystem.model;

import org.springframework.core.annotation.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Order(3)
@Data
@Entity
public class Specialization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialization_seq")
    @SequenceGenerator(name = "specialization_seq", sequenceName = "SPECIALIZATION_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(length=50, nullable=false)
    private String name;

    private String description;
    private String imageUrl;

}