package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.testsEntities.*;
import com.example.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Service
public class TestController {
    SequenceTestRepo sequenceTestRepo;
    VerbalTestRepo verbalTestRepo;
    NumberTestRepo numberTestRepo;
    VisualTestRepo visualTestRepo;

    @Autowired
    public TestController(SequenceTestRepo sequenceTestRepo, VerbalTestRepo verbalTestRepo, NumberTestRepo numberTestRepo, VisualTestRepo visualTestRepo) {
        this.sequenceTestRepo = sequenceTestRepo;
        this.verbalTestRepo = verbalTestRepo;
        this.numberTestRepo = numberTestRepo;
        this.visualTestRepo = visualTestRepo;
    }

    @PostMapping("/result/sequence")
    ResponseEntity<String> addResultSequenceTest(@RequestBody SequenceTest test) {
        if (test.isScoreValid()) {
            sequenceTestRepo.save((SequenceTest) modifyTest(test));

            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/result/verbal")
    ResponseEntity<String> addResultVerbalTest(@RequestBody VerbalTest test) {
        if (test.isScoreValid()) {
            verbalTestRepo.save((VerbalTest) modifyTest(test));

            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/result/number")
    ResponseEntity<String> addResultNumberTest(@RequestBody NumberTest test) {
        if (test.isScoreValid()) {
            numberTestRepo.save((NumberTest) modifyTest(test));

            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/result/visual")
    ResponseEntity<String> addResultVisualTest(@RequestBody VisualTest test) {
        if (test.isScoreValid()) {
            visualTestRepo.save((VisualTest) modifyTest(test));

            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    AppTest modifyTest(AppTest test) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            System.out.println(authentication.getPrincipal().getClass());

            AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();
            test.setIdUser(currentUser.getId());

        } else {
            test.setIdUser(0L);
        }
        test.setDateOfSubmission(new Date());
        return test;
    }
}

