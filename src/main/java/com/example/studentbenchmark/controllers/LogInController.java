package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class LogInController {
    private final UserRepo userRepo;

    @Autowired
    public LogInController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody loginRequest request) {
        AppUser user = userRepo.findByEmail(request.email());

        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        if (!user.getPassword().equals(request.password())) {
            return new ResponseEntity<>("Incorrect user password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);

    }

    private record loginRequest(String email, String password) {
    }

}
