package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Service
public class ChangeUserPasswordController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public ChangeUserPasswordController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/changeUserPassword")
    public ResponseEntity<String> changeUserPassword(@RequestBody ChangeUserPasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

            if (!passwordEncoder.matches(request.oldPassword(), currentUser.getPassword())) {
                return new ResponseEntity<>("Received incorrect user old password", HttpStatus.BAD_REQUEST);
            }

            if (!request.newPassword().equals(request.newPasswordRepeated())) {
                return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
            }

            userRepo.changeUserPassword(currentUser.getId(), passwordEncoder.encode(request.newPassword()));
            logsRepo.save(new LoggerEntity(currentUser.getUsername(), sqlDate, "User has changed the password"));
            logger.info("User has changed the password");
            return new ResponseEntity<>("User password changed successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private record ChangeUserPasswordRequest(String oldPassword, String newPassword,
                                             String newPasswordRepeated) {
    }
}
