    
    
package com.project.medicalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentStatusDistributionWithSpecialization {    

    String specializations;

    Long pending;

    Long cancelled;

    Long completed;

} 

    
  
