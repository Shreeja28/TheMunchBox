package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	private ProductRepository productRepository;

	// Add new product
	public void addProduct(Product p) {
		this.productRepository.save(p);
	}

	// Get all products
	public List<Product> getAllProducts() {
		return (List<Product>) this.productRepository.findAll();
	}

	// Get product by ID
	public Product getProduct(int id) {
		return this.productRepository.findById(id).orElse(null);
	}

	// ✅ Update product by ID
	public void updateProduct(Product updatedProduct, int id) {
		Optional<Product> optional = this.productRepository.findById(id);
		if (optional.isPresent()) {
			Product existingProduct = optional.get();
			existingProduct.setPname(updatedProduct.getPname());
			existingProduct.setPprice(updatedProduct.getPprice());
			this.productRepository.save(existingProduct);
		}
	}

	// Delete product by ID
	public void deleteProduct(int id) {
		this.productRepository.deleteById(id);
	}

	// Get product by name
	public Product getProductByName(String name) {
		return this.productRepository.findByPname(name);
	}
}
