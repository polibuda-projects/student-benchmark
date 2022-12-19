package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.entity.SupportMessage;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.SupportRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Service
@RestController
public class AdminDashboardController {

    private final LogsRepo logsRepo;
    private final SupportRepo supportRepo;

    @Autowired
    public AdminDashboardController(LogsRepo logsRepo, SupportRepo supportRepo) {
        this.logsRepo = logsRepo;
        this.supportRepo = supportRepo;
    }

    @GetMapping("/adminDashboard/logs")
    public ResponseEntity<String> adminDashboardLogs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if (currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            List<LoggerEntity> log = logsRepo.findAll();
            Gson gson = new Gson();
            String json = gson.toJson(log);
            return ResponseEntity.ok(json);
        } else {
            throw new RuntimeException("You are not authorized to view this page");
        }
    }

    @GetMapping("/adminDashboard/messages")
    public ResponseEntity<String> adminDashboardMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if (currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            List<SupportMessage> messages = supportRepo.findAll();
            Gson gson = new Gson();
            String json = gson.toJson(messages);
            return ResponseEntity.ok(json);
        } else {
            throw new RuntimeException("You are not authorized to view this page");
        }
    }
}
