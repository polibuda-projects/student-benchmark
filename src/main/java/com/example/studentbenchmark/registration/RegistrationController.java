package com.example.studentbenchmark.registration;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class RegistrationController {
    private UserRepo userRepo;

    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser user) {

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("User with this email already exists", HttpStatus.BAD_REQUEST);
        }
        userRepo.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
