package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.beans.PersonDetails;

@Repository
public interface IPersonRepository extends JpaRepository<PersonDetails, String> {

	@Query("select username from PersonDetails where category=?1 and role='Engineer'")
	List<String> findEngineers(String category);

	@Query("from PersonDetails where role='Engineer'")
	List<PersonDetails> findEngineerList();
	
}
