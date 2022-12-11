package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.PasswordResetToken;
import com.example.studentbenchmark.repository.PasswordResetTokenRepo;
import com.example.studentbenchmark.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@Service
public class PasswordRecoveryController {
    private final UserRepo userRepo;
    private final PasswordResetTokenRepo tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    @Autowired
    public PasswordRecoveryController(UserRepo userRepo, PasswordResetTokenRepo tokenRepo, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @PostMapping("/passwordRecovery")
    public ResponseEntity<String> passwordRecovery(HttpServletRequest request, @RequestBody PasswordRecoveryRequest requestPwd) {
        AppUser user = userRepo.findByEmail(requestPwd.email());
        if (user == null) {
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

        return new ResponseEntity<>("Password reset email sent successfully", HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token", required = false) String token,
                                                @RequestBody ResetPasswordRequest request) {
        PasswordResetToken passwordResetToken = tokenRepo.findByToken(token).orElse(null);
        if (passwordResetToken == null) {
            return new ResponseEntity<>("Token not found", HttpStatus.BAD_REQUEST);
        }
        if (passwordResetToken.getExpiryDate().before(new Date())) {
            return new ResponseEntity<>("Token expired", HttpStatus.FORBIDDEN);
        }
        if (!request.newPassword().equals(request.newPasswordRepeated())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        AppUser user = passwordResetToken.getUser();
        userRepo.changeUserPassword(user.getIdUser(), passwordEncoder.encode(request.newPassword()));
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    private record ResetPasswordRequest(String newPassword,
                                        String newPasswordRepeated) {
    }

    private record PasswordRecoveryRequest(String email) {
    }
}
