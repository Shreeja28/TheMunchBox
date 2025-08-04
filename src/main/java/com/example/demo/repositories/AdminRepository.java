package com.example.demo.repositories;

import com.example.demo.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);
}
