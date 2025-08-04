package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import com.example.demo.count.Logic;
import com.example.demo.entities.*;
import com.example.demo.loginCredentials.*;
import com.example.demo.services.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

	@Autowired
	private UserServices services;

	@Autowired
	private AdminServices adminServices;

	@Autowired
	private ProductServices productServices;

	@Autowired
	private OrderServices orderServices;

	// Admin login
	@PostMapping("/adminLogin")
	public String getAllData(@ModelAttribute("adminLogin") AdminLogin login, Model model) {
		String email = login.getEmail();
		String password = login.getPassword();

		if (adminServices.validateAdminCredentials(email, password)) {
			return "redirect:/admin/services";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "Login";
		}
	}

	// User login
	@PostMapping("/userLogin")
	public String userLogin(@ModelAttribute("userLogin") UserLogin login, Model model, HttpSession session) {
		String email = login.getUserEmail();
		String password = login.getUserPassword();

		if (services.validateLoginCredentials(email, password)) {
			User user = services.getUserByEmail(email);
			session.setAttribute("loggedInUser", user);

			List<Orders> orders = orderServices.getOrdersForUser(user);
			model.addAttribute("orders", orders);
			model.addAttribute("name", user.getUname());
			return "BuyProduct";
		} else {
			model.addAttribute("error2", "Invalid email or password");
			return "Login";
		}
	}

	// Search product
	@PostMapping("/product/search")
	public String searchHandler(@RequestParam("productName") String name, Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			return "redirect:/login";
		}

		Product product = productServices.getProductByName(name);
		if (product == null) {
			model.addAttribute("message", "SORRY...!  Product Unavailable");
		} else {
			model.addAttribute("product", product);
		}

		List<Orders> orders = orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("name", user.getUname());

		return "BuyProduct";
	}

	// Admin dashboard
	@GetMapping("/admin/services")
	public String returnBack(Model model) {
		model.addAttribute("users", services.getAllUser());
		model.addAttribute("admins", adminServices.getAll());
		model.addAttribute("products", productServices.getAllProducts());
		model.addAttribute("orders", orderServices.getOrders());
		return "Admin_Page";
	}

	// Add admin page
	@GetMapping("/addAdmin")
	public String addAdminPage() {
		return "Add_Admin";
	}

	// Add admin
	@PostMapping("/addingAdmin")
	public String addAdmin(@ModelAttribute Admin admin) {
		adminServices.addAdmin(admin);
		return "redirect:/admin/services";
	}

	// Update admin
	@GetMapping("/updateAdmin/{adminId}")
	public String update(@PathVariable("adminId") int id, Model model) {
		model.addAttribute("admin", adminServices.getAdmin(id));
		return "Update_Admin";
	}

	@GetMapping("/updatingAdmin/{id}")
	public String updateAdmin(@ModelAttribute Admin admin, @PathVariable("id") int id) {
		adminServices.update(admin, id);
		return "redirect:/admin/services";
	}

	// Delete admin
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable("id") int id) {
		adminServices.delete(id);
		return "redirect:/admin/services";
	}

	// Add product page
	@GetMapping("/addProduct")
	public String addProduct() {
		return "Add_Product";
	}

	// Update product page
	@GetMapping("/updateProduct/{productId}")
	public String updateProduct(@PathVariable("productId") int id, Model model) {
		model.addAttribute("product", productServices.getProduct(id));
		return "Update_Product";
	}

	// Add user page
	@GetMapping("/addUser")
	public String addUser() {
		return "Add_User";
	}

	// Update user
	@GetMapping("/updateUser/{userId}")
	public String updateUserPage(@PathVariable("userId") int id, Model model) {
		model.addAttribute("user", services.getUser(id));
		return "Update_User";
	}

	// Place order
	@PostMapping("/product/order")
	public String orderHandler(@ModelAttribute Orders order, Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			return "redirect:/login";
		}

		double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		order.setOrderDate(new Date());

		orderServices.saveOrder(order);

		model.addAttribute("amount", totalAmount);
		return "Order_success";
	}

	@GetMapping("/product/back")
	public String back(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("orders", orderServices.getOrdersForUser(user));
		model.addAttribute("name", user.getUname());
		return "BuyProduct";
	}
}
