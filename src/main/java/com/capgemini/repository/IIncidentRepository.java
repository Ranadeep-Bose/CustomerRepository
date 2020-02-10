package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.beans.IncidentDetails;

@Repository
public interface IIncidentRepository extends JpaRepository<IncidentDetails, Integer> {

	@Query("from IncidentDetails where raisedBy=?1 and status='Assigned'")
	List<IncidentDetails> findPendingIncidents(String username);
	
	@Query("from IncidentDetails where raisedBy=?1 and status='Solved'")
	List<IncidentDetails> findSolvedIncidents(String username);

	@Query("select COUNT(*) from IncidentDetails where assignedTo=?1 and raiseDate=?2")
	Integer findCount(String username, String raiseDate);
	
	@Query("from IncidentDetails where assignedTo=?1 and status='Assigned'")
	List<IncidentDetails> findUnsolvedIncidents(String username);

	@Query("from IncidentDetails where assignedTo=?1 and status='Solved'")
	List<IncidentDetails> showSolvedIncidents(String username);

	@Query("from IncidentDetails where assignedTo=?1")
	List<IncidentDetails> findIncidents(String username);

	
	
	
	
}
