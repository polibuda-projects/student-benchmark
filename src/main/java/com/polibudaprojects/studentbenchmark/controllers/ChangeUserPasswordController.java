package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@RestController
@Service
public class ChangeUserPasswordController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(ChangeUserPasswordController.class);

    @Autowired
    public ChangeUserPasswordController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/changeUserPassword")
    public ResponseEntity<String> changeUserPassword(@Valid @RequestBody ChangeUserPasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            AppUserEntityDetails userEntityDetails = (AppUserEntityDetails) authentication.getPrincipal();
            AppUser currentUser = userRepo.findByEmail(userEntityDetails.getEmail());

            if (!passwordEncoder.matches(request.oldPassword(), currentUser.getPassword())) {
                logger.error("User has entered incorrect old password");
                return new ResponseEntity<>("Received incorrect user old password", HttpStatus.BAD_REQUEST);
            }

            if (!request.newPassword().equals(request.newPasswordRepeated())) {
                logger.error("User has entered not matching passwords");
                return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
            }

            userRepo.changeUserPassword(currentUser.getIdUser(), passwordEncoder.encode(request.newPassword()));
            logsRepo.save(new LoggerEntity(currentUser.getNickname(), currentUser.getIdUser(), "User has changed the password"));
            logger.info("User has changed the password");
            return new ResponseEntity<>("User password changed successfully", HttpStatus.OK);
        }
        logger.error("User is unauthorized");
        return new ResponseEntity<>("User is unauthorized", HttpStatus.UNAUTHORIZED);
    }

    private record ChangeUserPasswordRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String oldPassword,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String newPassword,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String newPasswordRepeated) {
    }
}
