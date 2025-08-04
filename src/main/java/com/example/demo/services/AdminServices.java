package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.AdminRepository;

import com.example.demo.entities.Admin;

@Service
public class AdminServices {

	@Autowired
	private AdminRepository adminRepository;

	// ✅ Get all admins
	public List<Admin> getAll() {
		return (List<Admin>) adminRepository.findAll();
	}

	// ✅ Get admin by id
	public Admin getAdmin(int id) {
		return adminRepository.findById(id).orElse(null);
	}

	// ✅ Add new admin
	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	// ✅ Update admin
	public void update(Admin updatedAdmin, int id) {
		Optional<Admin> optionalAdmin = adminRepository.findById(id);
		if (optionalAdmin.isPresent()) {
			Admin existingAdmin = optionalAdmin.get();
			existingAdmin.setAdminName(updatedAdmin.getAdminName());
			existingAdmin.setAdminEmail(updatedAdmin.getAdminEmail());
			existingAdmin.setAdminNumber(updatedAdmin.getAdminNumber());
			existingAdmin.setAdminPassword(updatedAdmin.getAdminPassword());
			adminRepository.save(existingAdmin);
		}
	}

	// ✅ Delete admin
	public void delete(int id) {
		adminRepository.deleteById(id);
	}

	// ✅ Validate admin credentials cleanly
	public boolean validateAdminCredentials(String email, String password) {
		Admin admin = adminRepository.findByAdminEmailAndAdminPassword(email, password);
		return admin != null;
	}
}
