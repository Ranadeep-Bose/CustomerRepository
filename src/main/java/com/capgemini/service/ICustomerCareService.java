package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.beans.IncidentDetails;
import com.capgemini.beans.PersonDetails;
import com.capgemini.exception.CustomerCareException;

public interface ICustomerCareService {

	Optional<PersonDetails> getDetails(String username) throws CustomerCareException;

	IncidentDetails raiseIncident(IncidentDetails incident) throws CustomerCareException;

	List<String> getEngineerList(String category) throws CustomerCareException;

	Integer currentlyHandlingIncidents(String username, String raiseDate) throws CustomerCareException;

	List<IncidentDetails> trackIncidents(String username) throws CustomerCareException;

	List<IncidentDetails> viewSolvedIncidents(String username) throws CustomerCareException;

	List<IncidentDetails> showUnsolvedIncidents(String username) throws CustomerCareException;

	List<IncidentDetails> showSolvedIncidents(String username) throws CustomerCareException;

	IncidentDetails solve(IncidentDetails incidentDetails);

	IncidentDetails getIncidentDetails(Integer requestId) throws CustomerCareException;

	void cancel(Integer requestId) throws CustomerCareException;

	IncidentDetails rateReview(IncidentDetails incident) throws CustomerCareException;

	List<IncidentDetails> audit(String username) throws CustomerCareException;

	List<PersonDetails> showEngineerList();

	PersonDetails addEngineer(PersonDetails personDetails) throws CustomerCareException;

	PersonDetails createUser(PersonDetails personDetails) throws CustomerCareException;

}
