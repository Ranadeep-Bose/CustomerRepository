package com.capgemini.test;

/*import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.beans.IncidentDetails;
import com.capgemini.beans.PersonDetails;
import com.capgemini.exception.CustomerCareException;
import com.capgemini.repository.IIncidentRepository;
import com.capgemini.repository.IPersonRepository;
import com.capgemini.service.ICustomerCareService;

@RunWith(SpringRunner.class)
@SpringBootTest*/
public class CustomerCareApplicationTests {

	/*
	 * PersonDetails personDetails = new
	 * PersonDetails("sampriti","sampriti1234","Sampriti Dutta"
	 * ,"User","Software Install"); IncidentDetails incidentDetails = new
	 * IncidentDetails(1102, "Software Install", "Python", "sampriti", "03-02-2020",
	 * "gaurav", "03-02-2020", "Solved", 8, "Good"); Optional<PersonDetails> person
	 * = Optional.of(personDetails); Optional<IncidentDetails> incident =
	 * Optional.of(incidentDetails); List<String> engineerNameList;
	 * List<PersonDetails> personList; List<IncidentDetails> incidentList; Integer
	 * count;
	 * 
	 * @Autowired private ICustomerCareService service;
	 * 
	 * @MockBean private IPersonRepository personRepository;
	 * 
	 * @MockBean private IIncidentRepository incidentRepository;
	 * 
	 * @Test public void getDetails() throws CustomerCareException {
	 * when(personRepository.findById("sampriti")).thenReturn(person);
	 * assertEquals(person, service.getDetails("sampriti")); }
	 * 
	 * @Test public void raiseIncident() throws CustomerCareException {
	 * when(incidentRepository.save(incidentDetails)).thenReturn(incidentDetails);
	 * assertEquals(incidentDetails, service.raiseIncident(incidentDetails)); }
	 * 
	 * @Test public void getEngineerList() throws CustomerCareException {
	 * when(personRepository.findEngineers("Software Install")).thenReturn(
	 * engineerNameList); assertEquals(engineerNameList,
	 * service.getEngineerList("Software Install")); }
	 * 
	 * @Test public void currentlyHandlingIncidents() throws CustomerCareException {
	 * when(incidentRepository.findCount("gaurav", "03-02-2020")).thenReturn(count);
	 * assertEquals(count, service.currentlyHandlingIncidents("gaurav",
	 * "03-02-2020")); }
	 * 
	 * @Test public void trackIncidents() throws CustomerCareException {
	 * when(incidentRepository.findPendingIncidents("sampriti")).thenReturn(
	 * incidentList); assertEquals(incidentList,
	 * service.trackIncidents("sampriti")); }
	 * 
	 * @Test public void viewSolvedIncidents() throws CustomerCareException {
	 * when(incidentRepository.findSolvedIncidents("sampriti")).thenReturn(
	 * incidentList); assertEquals(incidentList,
	 * service.viewSolvedIncidents("sampriti")); }
	 * 
	 * @Test public void showUnsolvedIncidents() throws CustomerCareException {
	 * when(incidentRepository.findUnsolvedIncidents("gaurav")).thenReturn(
	 * incidentList); assertEquals(incidentList,
	 * service.showUnsolvedIncidents("gaurav")); }
	 * 
	 * @Test public void showSolvedIncidents() throws CustomerCareException {
	 * when(incidentRepository.showSolvedIncidents("gaurav")).thenReturn(
	 * incidentList); assertEquals(incidentList,
	 * service.showSolvedIncidents("gaurav")); }
	 * 
	 * @Test public void solve() {
	 * when(incidentRepository.save(incidentDetails)).thenReturn(incidentDetails);
	 * assertEquals(incidentDetails, service.solve(incidentDetails)); }
	 * 
	 * @Test public void getIncidentDetails() throws CustomerCareException {
	 * when(incidentRepository.findById(1000)).thenReturn(incident);
	 * assertEquals(incident.get(), service.getIncidentDetails(1000)); }
	 * 
	 * @Test public void rateReview() throws CustomerCareException {
	 * when(incidentRepository.save(incidentDetails)).thenReturn(incidentDetails);
	 * assertEquals(incidentDetails, service.rateReview(incidentDetails)); }
	 * 
	 * @Test public void audit() throws CustomerCareException {
	 * when(incidentRepository.findIncidents("gaurav")).thenReturn(incidentList);
	 * assertEquals(incidentList, service.audit("gaurav")); }
	 * 
	 * @Test public void showEngineerList() {
	 * when(personRepository.findEngineerList()).thenReturn(personList);
	 * assertEquals(personList, service.showEngineerList()); }
	 * 
	 * @Test public void addEngineer() throws CustomerCareException {
	 * when(personRepository.save(personDetails)).thenReturn(personDetails);
	 * assertEquals(personDetails, service.addEngineer(personDetails)); }
	 * 
	 * @Test public void createUser() throws CustomerCareException {
	 * when(personRepository.save(personDetails)).thenReturn(personDetails);
	 * assertEquals(personDetails, service.createUser(personDetails)); }
	 */
}
