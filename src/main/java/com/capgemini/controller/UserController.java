package com.capgemini.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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


/**
 * This Class contains different methods
 * @author Capgemini
 * @since 06/07/2019
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
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
	
	@PostMapping("/userLogin")
    public String authenticateUser(@RequestParam String username, @Valid @RequestParam String password) throws CustomerCareException {
		
		Optional<PersonDetails> optional=service.getDetails(username);
		if(optional.isPresent()) {
		this.username=optional.get().getUsername();
		if(optional.get().getRole().equals("User")) {
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
	
	@PostMapping("/createUser")
	public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String name) {
		String message="";
		Optional<PersonDetails> details=null;
		PersonDetails personDetails = new PersonDetails();
		try {
			details=service.getDetails(username);
			
			if(!details.isPresent()) {
				if(Pattern.matches("[A-Za-z0-9]{8,}", password)) {
					personDetails.setUsername(username);
					personDetails.setName(name);
					personDetails.setRole("User");
					personDetails.setCategory("");				
					personDetails.setPassword(passwordEncoder.encode(password));
				try {
				service.createUser(personDetails);
				message="User Registered Successfully...";
				}catch (CustomerCareException e) {
					message=e.getMessage();
				}
				}else {
					message="Password should contain characters and numbers and minimum length is 8";
				}
			}else {
				message="Same Username Already Used...";
			}
		} catch (CustomerCareException exception) {		
			message=exception.getMessage();
		}
		
		 return message;
	}
	
	@PostMapping("/raiseIncident")
	@PreAuthorize("hasAuthority('User')")
	public String raiseIncident(@RequestParam String category, @RequestParam String details) {
		String message="";
		if(this.username!=null) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = dateFormat.format(new Date());
		IncidentDetails incidentDetails;
		IncidentDetails incident= new IncidentDetails();
			String engineerName=assignSupportEngineer(category, date);
			if (engineerName.equals("error")) {
				message="Category Must be one of the following: 'Software Install', 'Software Uninstall', 'Software Issue', 'Software Transfer'";
			} else {

			if(!engineerName.equals("")) {
				incident.setCategory(category);
				incident.setDetails(details);
				incident.setRaisedBy(this.username);
				incident.setAssignedTo(engineerName);
				incident.setRaiseDate(date);
				incident.setStatus("Assigned");
				try {
					incidentDetails=service.raiseIncident(incident);
					message="Your Request has been submitted with Request Id "+incidentDetails.getRequestId();
				} catch (CustomerCareException e) {
					
					message=e.getMessage();
				}
			}else {
				message="No Engineer is available today...Please come back tomorrow";
			}
			}
		}else {
			message=loginMessage;
		}
		return message;
		
	}
	
	
	private String assignSupportEngineer(String category, String raiseDate) {
		List<String> engineerList=null;
		String engineerName="";
		try {
			engineerList=service.getEngineerList(category);
			
			for (String username : engineerList) {
				Integer count=service.currentlyHandlingIncidents(username,raiseDate);
				if(count<3) {
					engineerName=username;
					break;
				}
			}
		} catch (CustomerCareException e) {
			engineerName="error";
		}
		
		return engineerName;
	}

	@GetMapping("/trackIncidents")
	@PreAuthorize("hasAuthority('User')")
	public String trackIncidents() throws CustomerCareException {
		
		List<IncidentDetails> list;
		if(this.username!=null) {
		list = service.trackIncidents(this.username);
		if(list.isEmpty()) {
			return "No Record Found...";
		}else {
			return list.toString();
		}
		}else {
			return loginMessage;
		}
		
}
	
	@DeleteMapping("/cancel/{requestId}")
	@PreAuthorize("hasAuthority('User')")
	public String cancel(@PathVariable Integer requestId) {
		String message="";
		IncidentDetails details;
		if(this.username!=null) {		
		try {
			details=service.getIncidentDetails(requestId);
			if(details.getStatus().equals("Solved")) {
				message="Incident already solved...";
			}else {
			try {
				service.cancel(requestId);
				message="Request Cancelled Successfully!!!";
			} catch (CustomerCareException e) {
				message=e.getMessage();
			}
		}
		} catch (CustomerCareException exception) {
			message=exception.getMessage();
		}
		}else {
			message=loginMessage;
		}
		return message;
	}
	
	@GetMapping("/viewSolvedIncidents")
	@PreAuthorize("hasAuthority('User')")
	public String viewSolvedIncidents() throws CustomerCareException {
		List<IncidentDetails> list;
		if(this.username!=null) {
		list = service.viewSolvedIncidents(this.username);
		if(list.isEmpty()) {
			return "No Record Found...";
		}else {
			return list.toString();
		}
		}else {
			return loginMessage;
		}	
	}
	
	@PutMapping("/rateReview")
	@PreAuthorize("hasAuthority('User')")
	public String rateReview(@RequestParam Integer requestId, @RequestParam Integer rating, @RequestParam String review) {
		IncidentDetails details;
		String message="";
		if(this.username!=null) {
			try {
				details=service.getIncidentDetails(requestId);
				
				if(details.getStatus().equals("Solved")) {
					details.setRating(rating);
					details.setReview(review);
					try {
						service.rateReview(details);
						message="Rating and Review Given...";
					} catch (CustomerCareException exception) {
						message=exception.getMessage();
					}
				}else {
					message="Cannot rate before the problem solved...";
				}
			} catch (CustomerCareException e) {
				message=e.getMessage();
			}
		}else {
			message=loginMessage;
		}
		return message;
		
	}
	
}
