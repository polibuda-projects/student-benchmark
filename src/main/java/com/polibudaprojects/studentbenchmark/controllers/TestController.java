package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.testsEntities.*;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.VisualTestRepo;
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
import java.util.*;
import java.util.stream.Collectors;


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

    public TestController() {
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

    @GetMapping("/tests/sequence")
    List<Long> getResultsFromSequenceTest() {
        List<Long> allPossibleScores = new ArrayList<>();
        Map<Integer, Long> scores = sequenceTestRepo.getAllScores().stream().collect(Collectors.groupingBy(p -> p.getScore(), Collectors.counting()));
        for (int i = 0; i <= SequenceTest.MAX_VALID_SCORE; i++) {
            if (!scores.containsKey(i)) {
                allPossibleScores.add(i, 0L);
            } else
                allPossibleScores.add(i, scores.get(i).longValue());
        }

        return allPossibleScores;
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

    @GetMapping("/tests/verbal")
    List<Long> getResultsFromVerbalTest() {
        List<Long> allPossibleScores = new ArrayList<>();
        Map<Integer, Long> scores = verbalTestRepo.getAllScores().stream().collect(Collectors.groupingBy(p -> p.getScore(), Collectors.counting()));
        for (int i = 0; i <= VerbalTest.MAX_VALID_SCORE; i++) {
            if (!scores.containsKey(i)) {
                allPossibleScores.add(i, 0L);
            } else
                allPossibleScores.add(i, scores.get(i).longValue());
        }

        return allPossibleScores;
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

    @GetMapping("/tests/number")
    List<Long> getResultsFromNumberTest() {
        List<Long> allPossibleScores = new ArrayList<>();
        Map<Integer, Long> scores = numberTestRepo.getAllScores().stream().collect(Collectors.groupingBy(p -> p.getScore(), Collectors.counting()));
        for (int i = 0; i <= NumberTest.MAX_VALID_SCORE; i++) {
            if (!scores.containsKey(i)) {
                allPossibleScores.add(i, 0L);
            } else
                allPossibleScores.add(i, scores.get(i).longValue());
        }

        return allPossibleScores;
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

    @GetMapping("/tests/visual")
    List<Long> getResultsFromVisualTest() {
        List<Long> allPossibleScores = new ArrayList<>();
        Map<Integer, Long> scores = visualTestRepo.getAllScores().stream().collect(Collectors.groupingBy(p -> p.getScore(), Collectors.counting()));
        for (int i = 0; i <= VisualTest.MAX_VALID_SCORE; i++) {
            if (!scores.containsKey(i)) {
                allPossibleScores.add(i, 0L);
            } else
                allPossibleScores.add(i, scores.get(i).longValue());
        }

        return allPossibleScores;
    }

    public AppTest modifyTest(AppTest test) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();
            test.setIdUser(currentUser.getId());

        } else {
            test.setIdUser(0L);
        }
        test.setDateOfSubmission(new Date());
        return test;
    }

    @GetMapping("/tests/verbalWords")
    public ArrayList<String> readFromCsvFile() {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/main/resources/ListOfWordsForVerbalTest.csv"))) {
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }

            Random rand = new Random();

            for (int i = 0; i < words.size(); i++) {
                int randomIndexToSwap = rand.nextInt(words.size());
                String temp = words.get(randomIndexToSwap);
                words.set(randomIndexToSwap, words.get(i));
                words.set(i, temp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return words;
    }

}

