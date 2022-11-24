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
public class ChangeUserPasswordController {
    private final UserRepo userRepo;

    @Autowired
    public ChangeUserPasswordController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/changeUserPassword")
    public ResponseEntity<String> registerUser(@RequestBody ChangeUserPasswordRequest request) {
        AppUser user = userRepo.findByEmail(request.email());
        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.UNAUTHORIZED);
        }
        if (!user.getPassword().equals(request.oldPassword())) {
            return new ResponseEntity<>("Incorrect user password", HttpStatus.BAD_REQUEST);
        }
        if (!request.newPassword().equals(request.newPasswordRepeated())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        userRepo.changeUserPassword(request.email(), request.newPassword());

        return new ResponseEntity<>("User password changed successfully", HttpStatus.OK);
    }

    private record ChangeUserPasswordRequest(String email, String oldPassword, String newPassword,
                                             String newPasswordRepeated) {
    }
}
