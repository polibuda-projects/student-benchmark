//package com.example.studentbenchmark.controllers;
//
//import com.example.studentbenchmark.entity.AppUser;
//import com.example.studentbenchmark.repository.ReportBugRepo;
//import com.example.studentbenchmark.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.Date;
//
//@RestController
//@Service
//public class ReportBugController {
//    private final ReportBugRepo reportBugRepo;
//    private final UserRepo userRepo;
//
//    @Autowired
//    public  ReportBugController(ReportBugRepo reportBugRepo, UserRepo userRepo) {
//        this.userRepo = userRepo;
//        this.reportBugRepo = reportBugRepo;
//    }
//
//    @PostMapping("/reportBug")
//    public ResponseEntity<String> Feedback(@RequestBody ReportBugController.ReportBugRequest request) {
//        AppUser user = userRepo.findByID(request.userID());
//        if (user == null) {
//            return new ResponseEntity<>("User does not exist", HttpStatus.UNAUTHORIZED);
//        }
//
//        reportBugRepo.newReportBug(request.userID(), request.message(), request.dateOfSubmission());
//
//        return new ResponseEntity<>("Report send successfully", HttpStatus.OK);
//
//    }
//
//    private record ReportBugRequest(Long userID, String message, Date dateOfSubmission ) {
//    }
//}
