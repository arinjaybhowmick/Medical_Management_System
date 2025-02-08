package com.project.medicalmanagementsystem.service;

import com.project.medicalmanagementsystem.dto.UserDTO;
import com.project.medicalmanagementsystem.dto.UserIdentificationDTO;

public interface UserIdentificationService {

    UserIdentificationDTO convertUserDTOtoUserIdentificationDTO(UserDTO userDTO);
}
