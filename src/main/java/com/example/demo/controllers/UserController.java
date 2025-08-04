package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired private UserServices userServices;

	@PostMapping("/add")
	public String addUser(@ModelAttribute User user) {
		userServices.addUser(user);
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/update/{id}")
	public String updateUserForm(@PathVariable int id, Model model) {
		model.addAttribute("user", userServices.getUser(id));
		return "Update_User";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable int id) {
		userServices.updateUser(user, id);
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		userServices.deleteUser(id);
		return "redirect:/admin/dashboard";
	}
}
