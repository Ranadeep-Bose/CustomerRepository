package com.capgemini.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.capgemini.beans.PersonDetails;



public class UserPrincipal implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2931150081875752276L;
	private String username;
	private String password;
	private String name;
	private String role;
	private String category;
	
	 private Collection<? extends GrantedAuthority> authorities;
	
	 
	 
	public UserPrincipal(String username, String password, String name, String role, String category,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.category = category;
		this.authorities = authorities;
	}
	
	 public static UserPrincipal create(PersonDetails person) {
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new SimpleGrantedAuthority(person.getRole()));

	        return new UserPrincipal(
	                person.getUsername(),
	                person.getPassword(),
	                person.getName(),
	                person.getRole(),
	                person.getCategory(),
	                authorities
	        );
	    }

	public String getUsername() {
		return username;
	}

	

	public String getPassword() {
		return password;
	}

	

	public String getName() {
		return name;
	}

	

	public String getRole() {
		return role;
	}

	

	public String getCategory() {
		return category;
	}

	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username);
    }

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
}

	


