package com.sporty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sporty.dao.UserDAO;
import com.sporty.pojo.User;

@Controller
public class LoginController {
 
	@Autowired
	private UserDAO userDAO;

	@RequestMapping("/login")
	public String login(Model theModel) {

		return "login";
	}
	
	@RequestMapping("/register")
	public String register(Model theModel) {

		return "register";
	}
	
	@PostMapping("/loginProcess")
	public String loginProcess(HttpServletRequest request, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		HttpSession session = request.getSession(true); 
		
		List<User> users = userDAO.getUsers();
		
		boolean isusesr = false;
		User tempUser = null;
	 	
		for (User user : users) {
			
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				isusesr = true;
				tempUser = user;
				 
				break;
			}
		}
			
			if (isusesr && tempUser.getType() == 0) {
				 
				session.setAttribute("currentUser", tempUser);
				return "redirect:/homepage/products";
			}
			
			else if (isusesr && tempUser.getType() == 1) {
				 
				session.setAttribute("currentUser", tempUser);
				return "redirect:/admin/products";
			}
			else
			return "login";
	}
	
	@PostMapping("/registerProcess")
	public String registerProcess(@RequestParam("username") String username,
			@RequestParam("age") String age,
			@RequestParam("password") String password) {
		
		User user = new User(username, password, Integer.parseInt(age));
		userDAO.saveUser(user);
		
		return "login";
	}
	
}
