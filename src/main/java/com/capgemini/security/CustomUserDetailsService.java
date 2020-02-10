package com.capgemini.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.beans.PersonDetails;
import com.capgemini.repository.IPersonRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	IPersonRepository personRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		PersonDetails person = personRepository.findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + username)
        );

        return UserPrincipal.create(person);
	}
	
	

}
