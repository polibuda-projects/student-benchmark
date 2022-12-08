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
    public ResponseEntity<String> Support(@RequestBody SupportController.supportRequest request) {
        if ((request.message()) == null) {
            return new ResponseEntity<>("No message body", HttpStatus.BAD_REQUEST);
        }

        //Pobieranie aktualnego uzytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if ((userRepo.findByNickname(currentUser.getUsername())) == null) {
            return new ResponseEntity<>("No email to send response", HttpStatus.UNAUTHORIZED);
        }

        supportRepo.save(new SupportMessage(request.message(), currentUser.getUsername(), currentUser.getId()));

        return new ResponseEntity<>("Support message send succesfully", HttpStatus.OK);
    }

    private record supportRequest(String message) {}

}
