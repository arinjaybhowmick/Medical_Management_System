package com.project.medicalmanagementsystem.model;

import org.springframework.core.annotation.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Order(1)
@Data
@Entity
public class Role {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
  @SequenceGenerator(name = "role_seq", sequenceName = "ROLE_SEQ", allocationSize = 1)
  private Long id;

  @NotBlank
  @Column(length = 20, nullable = false)
  private String roleName;

}
