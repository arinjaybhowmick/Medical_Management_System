package com.project.medicalmanagementsystem.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.medicalmanagementsystem.exception.UsernameAlreadyExistsException;
import com.project.medicalmanagementsystem.config.JwtGeneratorValidator;
import com.project.medicalmanagementsystem.dto.UserDTO;
import com.project.medicalmanagementsystem.model.Users;
import com.project.medicalmanagementsystem.repository.UsersRepository;
import com.project.medicalmanagementsystem.service.DefaultUserService;
import com.project.medicalmanagementsystem.service.GenerateResponseService;
import com.project.medicalmanagementsystem.service.RegisterLoginService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthorizationController {

	@Autowired
	UsersRepository userRepo;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtGeneratorValidator jwtGenVal;
	
	@Autowired
	BCryptPasswordEncoder bcCryptPasswordEncoder;
	
	@Autowired
	RegisterLoginService registerLoginService;

	@Autowired
	GenerateResponseService generateResponseService;

	@Autowired
	DefaultUserService defaultUserService;

	@PostMapping("/auth/registration")
	public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDto) {
		if (userRepo.existsByUserName(userDto.getUserName())) {
			throw new UsernameAlreadyExistsException("Username already exists: " + userDto.getUserName());
        }
		
		Users users =  registerLoginService.save(userDto);
		
		if (users.equals(null))
			return generateResponseService.generateResponse("Not able to save user ", HttpStatus.BAD_REQUEST, userDto);
		else
			return generateResponseService.generateResponse("User saved successfully : " + users.getId(), HttpStatus.OK, users);
	}

	@PostMapping("/auth/login")
	public ResponseEntity<Object> generateJwtToken(@RequestBody UserDTO userDto) throws Exception{
		return generateResponseService.generateResponse("Login Successful!", HttpStatus.OK, registerLoginService.login(userDto));
		
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<Object> refreshJwtToken(@RequestBody String refToken) throws Exception{
		System.out.println("refreshTokenRequest : " + refToken);
		//return ResponseEntity.ok(registerLoginService.refreshToken(refToken));
		return generateResponseService.generateResponse("Refresh token !", HttpStatus.OK,(registerLoginService.refreshToken(refToken)));
	}

	
	
	@GetMapping("/current-user")
	public ResponseEntity<String> getCurrentUser(HttpServletRequest req) {
        String currentUser = req.getUserPrincipal().getName();
        String jsonCurrentUser = "{\"currentUser\": \"" + currentUser + "\"}";
        return ResponseEntity.ok(jsonCurrentUser);
    }


	@GetMapping("/auth/welcomeAdmin")
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String welcome(Principal p) {
		return "WelcomeAdmin "+p.getName();
	}

	@GetMapping("/auth/welcomeDoctor")
	// @PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String welcomeDoctor() {
		return "Welcome Doctor ";
	}

	@GetMapping("/welcomePatient")
	// @PreAuthorize("hasAuthority('ROLE_PATIENT')")
	public String welcomePatient(Principal p) {
		return "Welcome Patient "+p.getName();
	}


	//  @ExceptionHandler(BadCredentialsException.class)
    // public String exceptionHandler() {
    //     return "Credentials Invalid !!";
    // }
	
	// public ResponseEntity<Object> generateRespose(String message, HttpStatus st, Object responseobj) {

	// 	Map<String, Object> map = new HashMap<String, Object>();
	// 	map.put("meaasge", message);
	// 	map.put("Status", st.value());
	// 	// map.put("data", responseobj);
	// 	map.put("cookies", responseobj);
	// 	return new ResponseEntity<Object>(map, st);
	// }

}
