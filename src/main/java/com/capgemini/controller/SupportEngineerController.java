package com.capgemini.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.beans.IncidentDetails;
import com.capgemini.beans.PersonDetails;
import com.capgemini.exception.CustomerCareException;
import com.capgemini.security.JwtTokenProvider;
import com.capgemini.service.ICustomerCareService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SupportEngineerController {

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
	
	@PostMapping("/engineerLogin")
	public String authenticateUser(@RequestParam String username, @Valid @RequestParam String password) throws CustomerCareException {
		
		Optional<PersonDetails> optional=service.getDetails(username);
		if(optional.isPresent()) {
		this.username=optional.get().getUsername();
		if(optional.get().getRole().equals("Engineer")) {
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
	
	@GetMapping("/showUnsolvedIncidents")
	@PreAuthorize("hasAuthority('Engineer')")
	public String showUnsolvedIncidents() throws CustomerCareException{
		List<IncidentDetails> list;
		if(this.username!=null) {
		list = service.showUnsolvedIncidents(this.username);
		if(list.isEmpty()) {
			return "No Record Found...";
		}else {
			return list.toString();
		}
		}else {
			return loginMessage;
		}
	}

	
	
	@PutMapping("/solveIncident/{requestId}")
	@PreAuthorize("hasAuthority('Engineer')")
	public String solve(@PathVariable Integer requestId) {
		String message="";
		IncidentDetails details;
		if(this.username!=null) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(new Date());
		try {
			details=service.getIncidentDetails(requestId);
			if(details.getStatus().equals("Assigned")) {
				details.setSolveDate(date);
				details.setStatus("Solved");
				service.solve(details);
				message="Incident Solved...";
			}else {
				message="Incident already Solved...";
			}
		}catch (CustomerCareException e) {
			message=e.getMessage();
		}
		}else {
			message=loginMessage;
		}
		return message;
	}
	
	@GetMapping("/showSolvedIncidents")
	@PreAuthorize("hasAuthority('Engineer')")
	public String showSolvedIncidents() throws CustomerCareException {
		List<IncidentDetails> list;
		if(this.username!=null) {
		list = service.showSolvedIncidents(this.username);
		if(list.isEmpty()) {
			return "No Record Found...";
		}else {
			return list.toString();
		}
		}else {
			return loginMessage;
		}
	}
}
