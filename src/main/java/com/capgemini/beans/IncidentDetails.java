package com.capgemini.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class contains customer details variables
 * @author Capgemini
 * @since 04/07/2019
 */
@Table(name = "Incident_Details")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sequence")
	@SequenceGenerator(name="sequence",initialValue=1001,sequenceName="incidentId_sequence")
	private Integer requestId;
	private String category;
	private String details;
	private String raisedBy;
	private String raiseDate;
	private String assignedTo;
	private String solveDate;
	private String status;
	private Integer rating;
	private String review;
	
	
	@Override
	public String toString() {
		return "\n {\n   requestId = " + requestId + ",\n   category = " + category + ",\n   details = " + details + ",\n   raisedBy = " 
				+ raisedBy + ",\n   raiseDate = " + raiseDate + ",\n   assignedTo = " + assignedTo + ",\n   solveDate = " + solveDate +
				",\n   status = " + status + ",\n   rating = " + rating + ",\n   review = " + review + "\n }\n";
	}
	
	
	
	
}
