package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@RestController
@Service
public class RegistrationController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AppUser user) {

        if(user.getPassword().equals(user.getPasswordConfirmation())) {
            userRepo.save(new AppUser(user.getNickname(), user.getEmail(), passwordEncoder.encode(user.getPassword()), 0));
            logsRepo.save(new LoggerEntity(user.getNickname(), sqlDate, "User has successfully registered the account"));
            logger.info("User has successfully registered the account");
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);

    }

    private record RegistrationRequest(String nickname, String email, String password, String passwordConfirmation) {
    }
}




