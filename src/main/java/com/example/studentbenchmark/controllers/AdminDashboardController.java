package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.repository.LogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RestController
public class AdminDashboardController{

    private LogsRepo logsRepo;

    @Autowired
    public AdminDashboardController(LogsRepo logsRepo) {
        this.logsRepo = logsRepo;
    }

    @GetMapping("/adminDashboard")
    public List<LoggerEntity> adminDashboard() {
        List<LoggerEntity> log = logsRepo.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        if (currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            return log;
        } else {
            return null;
        }
    }
}
