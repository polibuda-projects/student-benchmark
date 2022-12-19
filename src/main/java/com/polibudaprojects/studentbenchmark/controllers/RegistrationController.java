package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.*;


@RestController
@Service
public class RegistrationController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest request) {
        if (!request.password().equals(request.passwordConfirmation())) {
            logger.error("User has entered not matching passwords");
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (request.email().equals("support@studentbenchmark.pl")) {
            logger.error("User has entered the email which is not allowed");
            return new ResponseEntity<>("5", HttpStatus.BAD_REQUEST);
        }

        if (userRepo.findByEmail(request.email()) != null) {
            logger.error("User has entered the email which is already in use");
            return new ResponseEntity<>("This email is used by existing account", HttpStatus.BAD_REQUEST);
        }

        if (userRepo.findByNickname(request.nickname()).isPresent()) {
            logger.error("User has entered the nickname which is already in use");
            return new ResponseEntity<>("This nickname is used by existing account", HttpStatus.BAD_REQUEST);
        }

        userRepo.save(new AppUser(request.nickname(), request.email(), passwordEncoder.encode(request.password()), AppUser.Role.USER));
        AppUser user = userRepo.findByEmail(request.email());

        logsRepo.save(new LoggerEntity(user.getNickname(), user.getIdUser(), "User has successfully registered the account"));
        logger.info("User has successfully registered the account");
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    private record RegistrationRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 64, message = "2")
            String nickname,

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String password,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String passwordConfirmation) {
    }
}





