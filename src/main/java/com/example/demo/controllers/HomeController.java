package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

	@Autowired private ProductServices productServices;
	@Autowired private UserServices userServices;

	@GetMapping({"/", "/home"})
	public String home() { return "Home"; }

	@GetMapping("/products")
	public String products(Model model) {
		model.addAttribute("products", productServices.getAllProducts());
		return "Products";
	}

	@GetMapping("/about")
	public String about() { return "About"; }

	@GetMapping("/location")
	public String location() { return "Locate_us"; }

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("adminLogin", new AdminLogin());
		return "Login";
	}

	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user) {
		userServices.addUser(user);
		return "redirect:/login";
	}

	@PostMapping("/customerLogin")
	public String customerLogin(@RequestParam String userEmail, @RequestParam String userPassword,
								HttpSession session, Model model) {
		User user = userServices.login(userEmail, userPassword);
		if (user != null) {
			session.setAttribute("loggedInUser", user);
			return "redirect:/products";
		}
		model.addAttribute("error2", "Invalid email or password");
		model.addAttribute("adminLogin", new AdminLogin());
		return "Login";
	}
}
