package com.example.studentbenchmark.controllers;


import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.entity.SupportMessage;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.SupportRepo;
import com.example.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@RestController
@Service
public class SupportController {
    private final UserRepo userRepo;
    private final SupportRepo supportRepo;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(SupportController.class);

    @Autowired
    SupportController(UserRepo userRepo, SupportRepo supportRepo, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.supportRepo = supportRepo;
        this.logsRepo = logsRepo;
    }


    @PostMapping("/support")
    public ResponseEntity<String> Support(@Valid @RequestBody SupportRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();
        System.out.println(userRepo.findByNickname(currentUser.getUsername()));
        if ((userRepo.findByNickname(currentUser.getUsername())).isEmpty()) {
            return new ResponseEntity<>("No email to send response", HttpStatus.UNAUTHORIZED);
        }

        supportRepo.save(new SupportMessage(request.message(), request.messageTitle(), currentUser.getEmail(), currentUser.getId()));
        logsRepo.save(new LoggerEntity(currentUser.getUsername(), currentUser.getId(), "User has send the support message"));
        logger.info("User has send the support message");
        return new ResponseEntity<>("Support message send succesfully", HttpStatus.OK);
    }

    private record SupportRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 5, max = 256, message = "2")
            String message,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 64, message = "2")
            String messageTitle) {
    }
}
