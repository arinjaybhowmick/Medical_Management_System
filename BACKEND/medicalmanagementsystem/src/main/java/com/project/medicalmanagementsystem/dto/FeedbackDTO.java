package com.project.medicalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    
    private Integer rating;
    
    private String review;
    
    private Long appointment_id;

}
