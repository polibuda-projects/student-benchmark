package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.repository.UserRepo;
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

    @Autowired
    public ChangeUserPasswordController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/changeUserPassword")
    public ResponseEntity<String> registerUser(@RequestBody ChangeUserPasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

            if (!currentUser.getPassword().equals(passwordEncoder.encode(request.oldPassword()))) {
                return new ResponseEntity<>("Received incorrect user old password", HttpStatus.BAD_REQUEST);
            }

            if (!request.newPassword().equals(request.newPasswordRepeated())) {
                return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
            }

            userRepo.changeUserPassword(currentUser.getId(), passwordEncoder.encode(request.newPassword()));

            return new ResponseEntity<>("User password changed successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private record ChangeUserPasswordRequest(String oldPassword, String newPassword,
                                             String newPasswordRepeated) {
    }
}
