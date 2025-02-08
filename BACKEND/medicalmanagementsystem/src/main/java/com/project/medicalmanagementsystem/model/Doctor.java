package com.project.medicalmanagementsystem.model;

import java.time.LocalDate;
import org.springframework.core.annotation.Order;

import com.project.medicalmanagementsystem.utility.enums.Gender;
import com.project.medicalmanagementsystem.utility.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Order(5)
@Data
@Entity
public class Doctor {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
  @SequenceGenerator(name = "doctor_seq", sequenceName = "DOCTOR_SEQ", allocationSize = 1)
  private Long id;

  @NotBlank
  @Column(length = 50, nullable = false)
  private String name;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 2)
  private Gender gender;

  @NotBlank
  @Column(nullable = false, length = 100)
  private String qualification;

  @NotBlank
  @Email
  @Column(length = 50, nullable = false)
  private String email;

  @NotNull
  @Min(value = 1, message = "Fees cannot be less than 1")
  @Column(nullable = false)
  private Integer fees;

  @NotBlank
  @Column(nullable = false)
  private String experienceStart;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "specialization_id", nullable = false)
  private Specialization specialization;

  @Column(nullable = true)
  private LocalDate leaveStart;

  @Column(nullable = true)
  private LocalDate leaveEnd;

  @NotNull
  @Max(value = 5, message = "Cannot be more than 5")
  @Min(value = 1, message = "Cannot be less than 1")
  @Column(nullable = false, length = 2, columnDefinition = "INT DEFAULT 5")
  private Integer rating;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Status status;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private Users user;

  private String profileImgUrl;

}
