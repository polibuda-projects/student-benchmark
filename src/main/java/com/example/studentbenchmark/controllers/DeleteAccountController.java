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
public class DeleteAccountController {
    private final UserRepo userRepo;

    @Autowired
    public DeleteAccountController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountRequest request) {
        AppUser user = userRepo.findByEmail(request.email());

        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.UNAUTHORIZED);
        }
        if (!user.getPassword().equals(request.password())) {
            return new ResponseEntity<>("Incorrect user password", HttpStatus.BAD_REQUEST);
        }

        userRepo.deleteAccount(request.email());

        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }

    private record DeleteAccountRequest(String email, String password) {
    }
}
