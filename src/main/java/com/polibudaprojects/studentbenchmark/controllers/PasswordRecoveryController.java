package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.entity.PasswordResetToken;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.PasswordResetTokenRepo;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;

@RestController
@Service
public class PasswordRecoveryController {
    private final UserRepo userRepo;
    private final PasswordResetTokenRepo tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(PasswordRecoveryController.class);

    @Autowired
    public PasswordRecoveryController(UserRepo userRepo, PasswordResetTokenRepo tokenRepo, PasswordEncoder passwordEncoder, JavaMailSender mailSender, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.logsRepo = logsRepo;
    }

    @PostMapping("/passwordRecovery")
    public ResponseEntity<String> passwordRecovery(HttpServletRequest request, @RequestBody PasswordRecoveryRequest requestPwd) {
        AppUser user = userRepo.findByEmail(requestPwd.email());
        if (user == null) {
            logger.error("User has entered invalid email address");
            return new ResponseEntity<>("Invalid email address", HttpStatus.BAD_REQUEST);
        }
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        Date date = new Date();
        date.setMinutes(date.getMinutes() + 30);
        token.setExpiryDate(date);
        tokenRepo.save(token);

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("support@studentbenchmark.pl");
        email.setTo(user.getEmail());
        email.setSubject("Password reset");
        email.setText(url + "/resetPassword?token=" + token.getToken());
        mailSender.send(email);
        logsRepo.save(new LoggerEntity(user.getNickname(), user.getIdUser(), "Password reset email has been sent to the user"));
        logger.info("Password reset email has been sent to the user");
        return new ResponseEntity<>("Password reset email sent successfully", HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token", required = false) String token,
                                                @Valid @RequestBody ResetPasswordRequest request) {
        PasswordResetToken passwordResetToken = tokenRepo.findByToken(token).orElse(null);
        if (passwordResetToken == null) {
            logger.error("Token not found");
            return new ResponseEntity<>("Token not found", HttpStatus.BAD_REQUEST);
        }
        if (passwordResetToken.getExpiryDate().before(new Date())) {
            logger.error("Token has expired");
            return new ResponseEntity<>("Token expired", HttpStatus.FORBIDDEN);
        }
        if (!request.newPassword().equals(request.newPasswordRepeated())) {
            logger.error("User has entered not matching passwords");
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        AppUser user = passwordResetToken.getUser();
        userRepo.changeUserPassword(user.getIdUser(), passwordEncoder.encode(request.newPassword()));
        tokenRepo.delete(passwordResetToken);
        logsRepo.save(new LoggerEntity(user.getNickname(), user.getIdUser(), "User's password has changed successfully"));
        logger.info("User's password has changed successfully");
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    private record ResetPasswordRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String newPassword,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String newPasswordRepeated) {
    }

    private record PasswordRecoveryRequest(
            @Email(message = "3")
            @Pattern(regexp=".+@.+\\..+", message="3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email) {
    }
}
