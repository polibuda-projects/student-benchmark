package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.entity.SupportMessage;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.SupportRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final LogsRepo logsRepo;
    private final SupportRepo supportRepo;

    @Autowired
    public AdminDashboardController(LogsRepo logsRepo, SupportRepo supportRepo) {
        this.logsRepo = logsRepo;
        this.supportRepo = supportRepo;
    }

    //Metoda po sprawdzeniu uprawnień, zwraca w formacie JSON spis logów
    @GetMapping("/adminDashboard/logs")
    public ResponseEntity<String> adminDashboardLogs() {
        //Pobranie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        //Sprawdzenie czy użytkownik jest adminem,
        // jeśli tak to zwrócenie listy logów,
        // jeśli nie rzucenie wyjątku
        if (currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            List<LoggerEntity> log = logsRepo.findAll();
            Gson gson = new Gson();
            String json = gson.toJson(log);
            return ResponseEntity.ok(json);
        } else {
            throw new RuntimeException("You are not authorized to view this page");
        }
    }

    //Metoda po sprawdzeniu uprawnień, zwraca w formacie JSON spis wiadomości z formularza support
    @GetMapping("/adminDashboard/messages")
    public ResponseEntity<String> adminDashboardMessages() {

        //Pobranie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        //Sprawdzenie czy użytkownik jest adminem,
        // jesli tak to zwraca wiadomości,
        // jeśli nie to rzucenie wyjątku
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
