package com.aptech.eProject.services;


import com.aptech.eProject.models.User;
import com.aptech.eProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthorUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Load user by email
	 * @param email the username identifying the user whose data is required.
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(),
					user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
		} else {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
	}
}