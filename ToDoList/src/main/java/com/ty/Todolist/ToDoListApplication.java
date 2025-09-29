package com.ty.Todolist;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ty.Todolist.entity.User;
import com.ty.Todolist.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ToDoListApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Create a test user if it doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(user);
            System.out.println("Test user created: admin / admin123");
        }
    }
}
