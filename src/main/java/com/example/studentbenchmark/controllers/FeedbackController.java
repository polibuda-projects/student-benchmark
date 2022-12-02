//package com.example.studentbenchmark.controllers;
//
//import com.example.studentbenchmark.entity.AppUser;
//import com.example.studentbenchmark.repository.FeedbackRepo;
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
//public class FeedbackController {
////    private final FeedbackRepo feedbackRepo;
//    private final UserRepo userRepo;
//
//    @Autowired
//    public  FeedbackController(FeedbackRepo feedbackRepo, UserRepo userRepo) {
//        this.userRepo = userRepo;
//        this.feedbackRepo = feedbackRepo;
//    }
//
//    @PostMapping("/feedback")
//    public ResponseEntity<String> Feedback(@RequestBody FeedbackController.feedbackRequest request) {
//        AppUser user = userRepo.findByID(request.userID());
//        if (user == null) {
//            return new ResponseEntity<>("User does not exist", HttpStatus.UNAUTHORIZED);
//        }
//
//        feedbackRepo.newFeedback(request.userID(), request.message(), request.rating(), request.dateOfSubmission());
//
//
//        return new ResponseEntity<>("Feedback send successfully", HttpStatus.OK);
//
//    }
//
//    private record feedbackRequest(Long userID, String message, int rating, Date dateOfSubmission) {
//    }
//}
