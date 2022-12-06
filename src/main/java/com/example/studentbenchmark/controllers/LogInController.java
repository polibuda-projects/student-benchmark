package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class LogInController {

    //NIE DZIAŁA
    /*
    private final UserRepo userRepo;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public LogInController(UserRepo userRepo, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody loginRequest request) {
        AppUser user = userRepo.findByEmail(request.email());

        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        if (!user.getPassword().equals(request.password())) {
            return new ResponseEntity<>("Incorrect user password", HttpStatus.UNAUTHORIZED);
        }
        logsRepo.save(new LoggerEntity(user.getNickname(), dt, "User has successfully logged in"));
        logger.info("User has successfully logged in");
        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);

    }

    private record loginRequest(String email, String password) {
    }
*/
}
