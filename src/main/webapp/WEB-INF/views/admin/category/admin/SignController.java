package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.Role;
import com.aptech.eProject.models.User;
import com.aptech.eProject.services.RoleService;
import com.aptech.eProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/admin/login")
public class SignController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	UserService userService;

	@GetMapping("")
	public ModelAndView register(ModelAndView model) {
		model.addObject("user", new User());
		model.setViewName("auth/sign");
		return model;
	}
	@PostMapping("")
	public String login (Model model, @Valid User user, BindingResult result){
		User existingUser = userService.findUserByEmail(user.getEmail());
		if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
			result.rejectValue("email", null, "Invalid email or password");
			return "auth/sign";
		}

		if (result.hasErrors()) {
				model.addAttribute("user", user);
				return "admin/usermanager/index";
			}

			if (result.hasErrors()) {
				return "auth/sign";
			}
			return "admin/usermanager/index";
		}

	/*@PostMapping("/register")
	public String createUser(ModelAndView model, @Valid User user, BindingResult result) {

		User existingUser = userService.findUserByEmail(user.getEmail());

		if (existingUser != null && existingUser.getEmail() != null
				&& !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null,
					"There is already an account registered with the same email");
		}

		if (result.hasErrors()) {
			model.addObject("user", user);
			return "auth/sign";
		}else {
			result.rejectValue("email", null,
					"Succeed");
		}

		List<Role> roles = new ArrayList<>();
		Role userRole = roleService.findRoleByName("USER");
		if (userRole == null) {
			userRole = new Role();
			userRole.setName("USER");
		}
		roles.add(userRole);
		user.setRoles(roles);
		userService.createUser(user);

		return "auth/sign";
	}*/
	}

