package com.aptech.eProject.services;

import com.aptech.eProject.controllers.client.auth.mails.MailUserVerificationCode;
import com.aptech.eProject.models.Profile;
import com.aptech.eProject.models.User;
import com.aptech.eProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	MailUserVerificationCode mailUserVerificationCode;


	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserById(Integer userId) {
		User user = userRepository.findById(userId).get();
		if (user.getProfile() == null || user.getProfile().getId() == 0) {
			user.setProfile(new Profile());
		}
		return user;
	}

	public void createUser(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

/*		ModelMapperUtil<User, SignUpRequest> mapper = new ModelMapperUtil<>();
		User user = mapper.mapToModel(new SignUpRequest(), new User());*/

	/*	mailUserVerificationCode.sendMail(user.getEmail(), "code");*/

		userRepository.save(user);
	}

	public void updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public long countUser() {
		return userRepository.count();
	}
	/*public AuthResponse login(LoginRequest loginReq) throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			String email = authentication.getName();
			User user = (User) userRepository.findByEmail(email);

			return AuthResponse.builder().token(jwtUtil.generateToken(user)).build();

		} catch (Exception e) {
			throw new Exception("email or password incorrect");
		}
	}*/

	public void delete(Integer userId) {
		userRepository.deleteById(userId);
	}

	public List<User> findByPayment(String email) {
		return userRepository.searchByEmail(email);
	}
}
