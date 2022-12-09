package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.testsEntities.*;
import com.example.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

@RestController
@Service
public class TestController {
    SequenceTestRepo sequenceTestRepo;
    VerbalTestRepo verbalTestRepo;
    NumberTestRepo numberTestRepo;
    VisualTestRepo visualTestRepo;



    Logger logger = LoggerFactory.getLogger(TestController.class);

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
            logger.info("Results added successfully");
            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }
        logger.error("Incorrect data");
        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/result/verbal")
    ResponseEntity<String> addResultVerbalTest(@RequestBody VerbalTest test) {
        if (test.isScoreValid()) {
            verbalTestRepo.save((VerbalTest) modifyTest(test));
            logger.info("Results added successfully");
            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }
        logger.error("Incorrect data");
        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/result/number")
    ResponseEntity<String> addResultNumberTest(@RequestBody NumberTest test) {
        if (test.isScoreValid()) {
            numberTestRepo.save((NumberTest) modifyTest(test));
            logger.info("Results added successfully");
            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }
        logger.error("Incorrect data");
        return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/result/visual")
    ResponseEntity<String> addResultVisualTest(@RequestBody VisualTest test) {
        if (test.isScoreValid()) {
            visualTestRepo.save((VisualTest) modifyTest(test));
            logger.info("Results added successfully");
            return new ResponseEntity<>("Result added successfully", HttpStatus.OK);
        }
        logger.error("Incorrect data");
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
    @GetMapping("/verbalTest")
    public ArrayList<String> readFromCsvFile() {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/main/resources/ListOfWordsForVerbalTest.csv"))) {
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return words;
    }
}
