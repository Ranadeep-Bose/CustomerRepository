package com.capgemini.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.beans.IncidentDetails;
import com.capgemini.beans.PersonDetails;
import com.capgemini.exception.CustomerCareException;
import com.capgemini.repository.IIncidentRepository;
import com.capgemini.repository.IPersonRepository;

@Service
public class CustomerCareServiceImpl implements ICustomerCareService {
	
	@Autowired
	IPersonRepository repository;
	
	@Autowired
	IIncidentRepository incidentRepository;

	String usernamePattern = "[a-z]{5,10}";
	String userNameErrorMessage="Username Should be of length 5 to 10 and contains characters only";
	
	String requestIdPattern="[0-9]{4,}";
	String requestIdErrorMessage="RequestId should contain digits only and starts from 1001";
	
	String pattern="[A-Za-z ]+";
	
	String[] category= {"Software Install","Software Uninstall","Software Issue","Software Transfer"};
	
	@Override
	public Optional<PersonDetails> getDetails(String username) throws CustomerCareException {
		
		Optional<PersonDetails> details=null;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		
		if(usernameCheck) {
			details=repository.findById(username);
		}else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return details;
	}

	@Override
	public IncidentDetails raiseIncident(IncidentDetails incident) throws CustomerCareException {
		IncidentDetails details=null;
		boolean detailCheck=Pattern.matches("[A-Za-z0-9 ]+", incident.getDetails());
		boolean usernameCheck=Pattern.matches(usernamePattern, incident.getRaisedBy());
		if(incident.getCategory().equals(category[0]) || incident.getCategory().equals(category[1]) || incident.getCategory().equals(category[2]) || incident.getCategory().equals(category[3])) {
			if(detailCheck) {
				if(usernameCheck) {
					details = incidentRepository.save(incident);
				}else {
					throw new CustomerCareException(userNameErrorMessage);
				}
			}else {
				throw new CustomerCareException("Name of the software should have proper alphabets or numbers.");
			}
			
		}else{
			throw new CustomerCareException("Category Must be one of the following: "+category[0]+", "+category[1]+", "+category[2]+", "+category[3]);
		}
		return details;
	}

	@Override
	public List<String> getEngineerList(String category) throws CustomerCareException {
		List<String> list = null;
		if(category.equals("Software Install") || category.equals("Software Uninstall") || category.equals("Software Issue") || category.equals("Software Transfer") ) {
			list = repository.findEngineers(category);
		}else {
			throw new CustomerCareException("Category Must be one of the following: 'Software Install', 'Software Uninstall', 'Software Issue', 'Software Transfer'");
		}		
		return list;
	}

	@Override
	public Integer currentlyHandlingIncidents(String username, String raiseDate) throws CustomerCareException {
		Integer count = 0;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		boolean dateCheck=Pattern.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}", raiseDate);
		if(usernameCheck) {
			if(dateCheck) {
				count = incidentRepository.findCount(username,raiseDate);
			}else {
				throw new CustomerCareException("");
			}
		}
		return count;
	}

	@Override
	public List<IncidentDetails> trackIncidents(String username) throws CustomerCareException {
		List<IncidentDetails> list = null;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		if(usernameCheck) {
			list = incidentRepository.findPendingIncidents(username);
		}else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return list;
	}

	@Override
	public List<IncidentDetails> viewSolvedIncidents(String username) throws CustomerCareException {
		List<IncidentDetails> list = null;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		if(usernameCheck) {
			list = incidentRepository.findSolvedIncidents(username);
		}else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return list; 
	}

	@Override
	public List<IncidentDetails> showUnsolvedIncidents(String username) throws CustomerCareException {
		List<IncidentDetails> list = null;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		if(usernameCheck) {
			list = incidentRepository.findUnsolvedIncidents(username);
		}else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return list;
	}

	@Override
	public List<IncidentDetails> showSolvedIncidents(String username) throws CustomerCareException {
		List<IncidentDetails> list = null;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		if(usernameCheck) {
			list = incidentRepository.showSolvedIncidents(username);
		}else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return list;
	}

	@Override
	public IncidentDetails solve(IncidentDetails incidentDetails) {
		return incidentRepository.save(incidentDetails);
	}

	@Override
	public IncidentDetails getIncidentDetails(Integer requestId) throws CustomerCareException {
		IncidentDetails incidentDetails=null;
		boolean requestIdCheck=Pattern.matches(requestIdPattern, requestId.toString());
		if (requestIdCheck) {
			try {
				incidentDetails=incidentRepository.findById(requestId).get();
			}catch(Exception exception){
				throw new CustomerCareException("Invalid Request Id");
			}
		}else {
			throw new CustomerCareException(requestIdErrorMessage);
		}
		return incidentDetails;
	}

	@Override
	public void cancel(Integer requestId) throws CustomerCareException {
		boolean requestIdCheck=Pattern.matches(requestIdPattern, requestId.toString());
		if (requestIdCheck) {
		try {
			incidentRepository.deleteById(requestId);
		}catch(Exception exception) {
			throw new CustomerCareException("Invalid Request Id");
		}
	}else {
		throw new CustomerCareException(requestIdErrorMessage);
	}
	}

	@Override
	public IncidentDetails rateReview(IncidentDetails details) throws CustomerCareException {
		boolean requestIdCheck=Pattern.matches(requestIdPattern, details.getRequestId().toString());
		boolean ratingCheck=Pattern.matches("[1-9]|10", details.getRating().toString());
		boolean reviewCheck=Pattern.matches(pattern, details.getReview());
		
		if (requestIdCheck) {
			if (ratingCheck) {
				if (reviewCheck) {
					return incidentRepository.save(details);
				} else {
					throw new CustomerCareException("Review should contain Characters only");
				}
			} else {
				throw new CustomerCareException("Rating should be between 1 and 10");
			}
		} else {
			throw new CustomerCareException(requestIdErrorMessage);
		}
		
	}

	@Override
	public List<IncidentDetails> audit(String username) throws CustomerCareException {
		List<IncidentDetails> list;
		boolean usernameCheck=Pattern.matches(usernamePattern, username);
		if(usernameCheck) {
			list = incidentRepository.findIncidents(username);						
		}else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return list;
	}

	@Override
	public List<PersonDetails> showEngineerList() {
		return repository.findEngineerList();
	}

	@Override
	public PersonDetails addEngineer(PersonDetails personDetails) throws CustomerCareException {
		PersonDetails details = null;
		boolean usernameCheck=Pattern.matches(usernamePattern, personDetails.getUsername());
		boolean nameCheck=Pattern.matches(pattern, personDetails.getName());
		if (usernameCheck) {
			if (nameCheck) {
				if(personDetails.getCategory().equals(category[0]) || personDetails.getCategory().equals(category[1]) || personDetails.getCategory().equals(category[2]) || personDetails.getCategory().equals(category[3]) ) {
				details=repository.save(personDetails);
				}else {
					throw new CustomerCareException("Category Must be one of the following: "+category[0]+", "+category[1]+", "+category[2]+", "+category[3]);
				}
			} else {
				throw new CustomerCareException("Name should contain Characters only");
			}
		} else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return details;
	}
	
	@Override
	public PersonDetails createUser(PersonDetails personDetails) throws CustomerCareException {
		PersonDetails details = null;
		boolean usernameCheck=Pattern.matches(usernamePattern, personDetails.getUsername());
		boolean nameCheck=Pattern.matches("[A-Za-z ]+", personDetails.getName());
		if (usernameCheck) {
			if (nameCheck) {
				details=repository.save(personDetails);
			} else {
				throw new CustomerCareException("Name should contain Characters only");
			}
		} else {
			throw new CustomerCareException(userNameErrorMessage);
		}
		return details;	
	}

	
}
