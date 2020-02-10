package com.capgemini.controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.beans.IncidentDetails;
import com.capgemini.beans.PersonDetails;
import com.capgemini.exception.CustomerCareException;
import com.capgemini.security.JwtTokenProvider;
import com.capgemini.service.ICustomerCareService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AdminController {

	@Autowired
	ICustomerCareService service;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	String username;
	String loginMessage="Please Login First";
	
	
	@PostMapping("/adminLogin")
	public String authenticateUser(@RequestParam String username, @Valid @RequestParam String password) throws CustomerCareException {
		
		Optional<PersonDetails> optional=service.getDetails(username);
		if(optional.isPresent()) {
		this.username=optional.get().getUsername();
		if(optional.get().getRole().equals("Admin")) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(optional.get().getUsername(),password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Your Bearer Token is : "+tokenProvider.generateToken(authentication);
		}else {
			return "Login Error";
		}
		}else {
			return "Invalid Username";
		}
    }
	
	@PostMapping("/assignEngineer")
	@PreAuthorize("hasAuthority('Admin')")
	public String addEngineer(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String category) {
		String message = "";
		Optional<PersonDetails> details;
		PersonDetails personDetails = new PersonDetails();
		if(this.username!=null) {
		try {
			details=service.getDetails(username);
			
			if(!details.isPresent()) {
				if(Pattern.matches("[A-Za-z0-9]{8,}", password)) {
					personDetails.setUsername(username);
					personDetails.setName(name);
					personDetails.setCategory(category);
					personDetails.setRole("Engineer");				
					personDetails.setPassword(passwordEncoder.encode(password));
				try {
				service.addEngineer(personDetails);
				message="Engineer Assigned Successfully";
				}catch (CustomerCareException e) {
					message=e.getMessage();
				}
				}else {
					message="Password should contain characters and numbers and minimum length is 8";
				}
			}else {
				message="Same Username Already Used";
			}
		} catch (CustomerCareException exception) {		
			message=exception.getMessage();
		}
		}else {
			message=loginMessage;
		}
		 return message;
	}
	
	@GetMapping("/findAllEngineer")
	@PreAuthorize("hasAuthority('Admin')")
	public String getEngineerList(){
		List<PersonDetails> list;
		if(this.username!=null) {
		list=service.showEngineerList();
		return list.toString();
		}else {
			return loginMessage;
		}
	}
	
	@GetMapping("/audit/{username}")
	@PreAuthorize("hasAuthority('Admin')")
	public String audit(@PathVariable String username){
		List<IncidentDetails> list = null;
		String message="";
		if(this.username!=null) {
		try {
			Optional<PersonDetails> person=service.getDetails(username);
			if(person.isPresent()) {
				if(person.get().getRole().equals("Engineer")) {
					try {
						list=service.audit(username);
						message=list.toString();
						if(list.isEmpty()) {
							message= "No Incident Assigned or Solved";
						}
					} catch (CustomerCareException e) {
						message= e.getMessage();
					}
				}else {
					message= "Please Enter the username of an Engineer";
				}
			}else {
				message= "Invalid Username";
			}
		} catch (CustomerCareException exception) {
			message= exception.getMessage();
		}

		return message;
		}else {
			return loginMessage;
		}
	}
}
