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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@RestController
@Service
public class DeleteAccountController {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(DeleteAccountController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public DeleteAccountController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<String> registerUser(@Valid @RequestBody DeleteAccountRequest request, HttpServletRequest http) throws ServletException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String email = ((AppUserEntityDetails) principal).getEmail();
        String password = ((AppUserEntityDetails) principal).getPassword();


        if (!passwordEncoder.matches(request.password(), password)) {
            logger.error("User has entered incorrect password");
            return new ResponseEntity<>("Incorrect user password", HttpStatus.BAD_REQUEST);
        }

        userRepo.deleteAccount(email);
        logsRepo.save(new LoggerEntity(((AppUserEntityDetails) principal).getUsername(), ((AppUserEntityDetails) principal).getId(), sqlDate, "User has deleted the account"));
        logger.info("User has deleted the account");
        http.logout();
        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }

    private record DeleteAccountRequest(
            @NotNull
            @NotBlank(message = "password is mandatory")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
                    "<br/>" + "   Password must contain at least one digit [0-9]." +
                            "<br/>" + "   Password must contain at least one lowercase Latin character [a-z]." +
                            "<br/>" + "    Password must contain at least one uppercase Latin character [A-Z]." +
                            "<br/>" + "    Password must contain at least one special character like ! @ # & ( )." +
                            "<br/>" + "    Password must contain a length of at least 8 characters and a maximum of 64 characters.")
            String password) {
    }
}