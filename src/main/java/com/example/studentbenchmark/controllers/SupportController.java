package com.example.studentbenchmark.controllers;


import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.SupportMessage;
import com.example.studentbenchmark.repository.SupportRepo;
import com.example.studentbenchmark.repository.UserRepo;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@RestController
@Service
public class SupportController {
    private final UserRepo userRepo;
    private final SupportRepo supportRepo;

    @Autowired
    SupportController(UserRepo userRepo, SupportRepo supportRepo){
        this.userRepo = userRepo;
        this.supportRepo = supportRepo;
    }



    @PostMapping("/support")
    public ResponseEntity<String> Support(@Valid @RequestBody SupportController.supportRequest request) {

        //Pobieranie aktualnego uzytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if ((userRepo.findByNickname(currentUser.getUsername())) == null) {
            return new ResponseEntity<>("No email to send response", HttpStatus.UNAUTHORIZED);
        }

        supportRepo.save(new SupportMessage(request.message(), request.messageTitle(), currentUser.getUsername(), currentUser.getId()));

        return new ResponseEntity<>("Support message send succesfully", HttpStatus.OK);
    }

    private record supportRequest(
            @NotNull
            @NotBlank
            @Size(min=5, max=256)
            String message,

            @NotNull
            @Size(max=64)
            String messageTitle,

            @Email
            @NotBlank
            @NotNull
            @Size(min=3, max=64)
            String email) {}

}
