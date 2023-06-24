package com.website.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.website.global.GlobalData;
import com.website.model.Role;
import com.website.model.User;
import com.website.repository.RoleRepository;
import com.website.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@PostMapping("/login")
	public String postLogin(@RequestParam("email") String email,@RequestParam("password") String password) {
		System.out.println(email);
		return "redirect/shop";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@ModelAttribute("user") User user,HttpServletRequest httpServletRequest)throws ServletException {
		
		String password=user.getPassword();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<Role> roles=new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
//		System.out.println(user.getEmail());
//		httpServletRequest.login(user.getEmail(), user.getPassword());
		return "redirect:/shop";
		
	}
	
}
