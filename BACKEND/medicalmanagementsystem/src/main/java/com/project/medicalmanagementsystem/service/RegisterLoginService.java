package com.project.medicalmanagementsystem.service;

import com.project.medicalmanagementsystem.dto.JwtAuthenticationResponse;
import com.project.medicalmanagementsystem.dto.UserDTO;
import com.project.medicalmanagementsystem.model.Users;

public interface RegisterLoginService {
    Users save(UserDTO userRegisteredDTO);

    JwtAuthenticationResponse login(UserDTO userDto) throws Exception;

    JwtAuthenticationResponse refreshToken(String refToken);
}
