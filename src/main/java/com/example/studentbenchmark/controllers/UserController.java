package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class UserController {
  @GetMapping("/user")
  ResponseEntity<String> getUserData() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AppUserEntityDetails userEntityDetails = (AppUserEntityDetails) authentication.getPrincipal();
    String username = userEntityDetails.getUsername();
    String role = userEntityDetails.getAuthorities().toArray()[0].toString();

    return new ResponseEntity<>("{\"username\": \"" + username + "\", \"role\": \"" + role + "\"}", HttpStatus.OK);
  }
}
