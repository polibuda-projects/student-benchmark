package com.example.studentbenchmark.controllers;


import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Service
public class DeleteAccountController {
    private final UserRepo userRepo;

    @Autowired
    public DeleteAccountController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<String> registerUser(@RequestBody DeleteAccountRequest request) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String email = ((AppUserEntityDetails)principal).getUsername();
        String password = ((AppUserEntityDetails)principal).getPassword();


        if (password.matches(request.password())) {
            return new ResponseEntity<>("Incorrect user password", HttpStatus.BAD_REQUEST);
        }

        userRepo.deleteAccount(email);

        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }

    private record DeleteAccountRequest(String password) {
    }
}