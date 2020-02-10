package com.capgemini.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDetails {

	@Id
	private String username;
	private String password;
	private String name;
	private String role;
	private String category;
	
	@Override
	public String toString() {
		return "\n {\n   username = " + username + ",\n   password = " + password + ",\n   name = " + name + ",\n   role = " + role
				+ ",\n   category = " + category + "\n }\n";
	}

	
	
	
	
	
	
}
