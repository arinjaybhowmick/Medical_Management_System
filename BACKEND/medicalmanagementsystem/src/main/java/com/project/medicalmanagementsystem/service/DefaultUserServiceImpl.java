package com.project.medicalmanagementsystem.service;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.medicalmanagementsystem.model.Role;
import com.project.medicalmanagementsystem.model.Users;
import com.project.medicalmanagementsystem.repository.DoctorRepository;
import com.project.medicalmanagementsystem.repository.RoleRepository;
import com.project.medicalmanagementsystem.repository.UsersRepository;


@Service
public class DefaultUserServiceImpl implements DefaultUserService{ 

	@Autowired
	UsersRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;

	@Autowired
	DoctorRepository doctorRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Users user = userRepo.findByUserName(username);
         Set<Role> roleSet=new HashSet<>();
         roleSet.add(user.getRole());
	     return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(roleSet));
	}
	
	public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}

	public String getStatus(String username){
        String status = doctorRepository.findStatusFromUsername(username).toString();
        return status;
    }

	public String getUserRole(String userName){
		Users user = userRepo.findByUserName(userName);
		String userRole = user.getRole().getRoleName();
		return userRole;
	}
	
}
