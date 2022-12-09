package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.*;


@RestController
@Service
public class RegistrationController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @Autowired
    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest request) {

        if(!request.password().equals(request.passwordConfirmation())) {
            logger.error("User has entered not matching passwords");
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (userRepo.findByEmail(request.email()) != null)
        {
            logger.error("User has entered the email which is already in use");
            return new ResponseEntity<>("This email is used by existing account", HttpStatus.BAD_REQUEST);
        }

        if (!userRepo.findByNickname(request.nickname()).isEmpty())
        {
            logger.error("User has entered the nickname which is already in use");
            return new ResponseEntity<>("This nickname is used by existing account", HttpStatus.BAD_REQUEST);
        }


        userRepo.save(new AppUser(request.nickname(), request.email(), passwordEncoder.encode(request.password()), AppUser.Role.USER));
        AppUser user = userRepo.findByEmail(request.email());
        logsRepo.save(new LoggerEntity(user.getNickname(), user.getIdUser(), sqlDate, "User has successfully registered the account"));
        logger.info("User has successfully registered the account");
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

    private record RegistrationRequest(
            @NotNull
            @NotBlank(message = "nickname is mandatory")
            @Size(min=3, max=64)
            String nickname,

            @Email
            @NotBlank
            @NotNull
            @Size(min=3, max=64)
            String email,

            @NotNull
            @NotBlank(message = "password is mandatory")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
                            "<br/>"+"   Password must contain at least one digit [0-9]." +
                            "<br/>"+"   Password must contain at least one lowercase Latin character [a-z]." +
                            "<br/>"+"    Password must contain at least one uppercase Latin character [A-Z]." +
                            "<br/>"+"    Password must contain at least one special character like ! @ # & ( )." +
                            "<br/>"+ "    Password must contain a length of at least 8 characters and a maximum of 64 characters.")
            String password,
            @NotNull
            @NotBlank(message = "password confirmation is mandatory")
            String passwordConfirmation) {
    }
}




