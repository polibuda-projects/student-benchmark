package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.entity.SupportMessage;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.SupportRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
public class AdminDashboardController {

    private LogsRepo logsRepo;
    private SupportRepo supportRepo;

    @Autowired
    public AdminDashboardController(LogsRepo logsRepo, SupportRepo supportRepo) {
        this.logsRepo = logsRepo;
        this.supportRepo = supportRepo;
    }

    @GetMapping("/adminDashboard/logs")
    public String adminDashboardLogs() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if (currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            List<LoggerEntity> log = logsRepo.findAll();
            Gson gson = new Gson();
            return gson.toJson(log);
        } else {
            throw new RuntimeException("You are not authorized to view this page");
        }
    }

    @GetMapping("/adminDashboard/messages")
    public String adminDashboardMessages() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if (currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            List<SupportMessage> messages = supportRepo.findAll();
            Gson gson = new Gson();
            return gson.toJson(messages);
        } else {
            throw new RuntimeException("You are not authorized to view this page");
        }
    }
}
