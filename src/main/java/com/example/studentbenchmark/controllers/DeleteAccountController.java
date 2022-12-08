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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@RestController
@Service
public class DeleteAccountController {
    private final UserRepo userRepo;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public DeleteAccountController(UserRepo userRepo, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@Valid @RequestBody DeleteAccountRequest request) {

       // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        //String email = ((AppUserEntityDetails) principal).getEmail();
       // String password = ((AppUserEntityDetails) principal).getPassword();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();


            if (currentUser.getPassword().matches(request.password())) {
                return new ResponseEntity<>("Incorrect user password", HttpStatus.BAD_REQUEST);
            }

            userRepo.deleteAccount(currentUser.getEmail());
            logsRepo.save(new LoggerEntity(currentUser.getEmail(), sqlDate, "User has deleted the account"));
            logger.info("User has deleted the account");
            return new ResponseEntity<>("User Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);

    }

    private record DeleteAccountRequest(
            @NotEmpty
            @NotBlank
            @NotNull
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$")
            String password) {
    }
}
