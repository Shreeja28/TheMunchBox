package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUemail(String email);

    // ✅ add this method
    User findByUemailAndUpassword(String email, String password);
}

