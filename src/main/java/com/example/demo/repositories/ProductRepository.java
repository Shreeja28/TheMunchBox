package com.example.demo.repositories;


import com.example.demo.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Product;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends CrudRepository<Product,Integer>
{
	public Product findByPname(String name);

	@Repository
	interface AdminRepository extends JpaRepository<Admin, Integer> {

		// Custom finder method by email
		Admin findByEmail(String email);
	}
}