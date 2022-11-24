package com.example.studentbenchmark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;


@RestController
@Service
public class SupportController {
    private final JavaMailSender emailSender;

    @Autowired
    public SupportController(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    @PostMapping("/support")
    public ResponseEntity<String> sendSupport(@RequestBody SupportRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(request.senderEmail());
        message.setTo(request.recieverEmail);
        message.setSubject(request.subject);
        message.setText(request.body());
        try{
            emailSender.send(message);
        }
        catch(MailException e){
            return new ResponseEntity<>("Email send failed", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("Email send successfully", HttpStatus.OK);
    }
    private record SupportRequest(String senderEmail, String recieverEmail, String subject, String body) {
    }

}
