package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

	@Autowired
	private ProductServices productServices;

	// Add product
	@PostMapping("/addingProduct")
	public String addingProduct(@ModelAttribute Product product) {
		productServices.addProduct(product);
		return "redirect:/admin/services";
	}

	// Update product
	@PostMapping("/updatingProduct/{id}")
	public String updatingProduct(@ModelAttribute Product product, @PathVariable("id") int id) {
		productServices.updateProduct(product, id);
		return "redirect:/admin/services";
	}

	// Delete product
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		productServices.deleteProduct(id);
		return "redirect:/admin/services";
	}
}
