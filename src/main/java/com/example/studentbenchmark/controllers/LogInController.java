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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Service
public class LogInController {


    private final UserRepo userRepo;

    private final LogsRepo logsRepo;

    private final PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public LogInController(UserRepo userRepo, LogsRepo logsRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.logsRepo = logsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody loginRequest request) {

        AppUser user = userRepo.findByEmail(request.email());


        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return new ResponseEntity<>("Incorrect user password" , HttpStatus.UNAUTHORIZED);
        }

        logsRepo.save(new LoggerEntity(user.getNickname(), sqlDate, "User has successfully logged in"));
        //sqlDate nie wiem czy dobrze, dalem zeby zrobic walidacje loginu :)
        logger.info("User has successfully logged in");
        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);

    }

    private record loginRequest(String email, String password) {
    }

}