package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.testsEntities.NumberTest;
import com.example.studentbenchmark.entity.testsEntities.SequenceTest;
import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import com.example.studentbenchmark.entity.testsEntities.VisualTest;
import com.example.studentbenchmark.repository.UserRepo;
import com.example.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Service
public class DashboardController {
    private final NumberTestRepo numberTestRepo;
    private final SequenceTestRepo sequenceTestRepo;
    private final VerbalTestRepo verbalTestRepo;
    private final VisualTestRepo visualTestRepo;

    @Autowired
    DashboardController(NumberTestRepo numberTestRepo, SequenceTestRepo sequenceTestRepo, VerbalTestRepo verbalTestRepo, VisualTestRepo visualTestRepo, UserRepo userRepo){
        this.numberTestRepo = numberTestRepo;
        this.sequenceTestRepo = sequenceTestRepo;
        this.verbalTestRepo = verbalTestRepo;
        this.visualTestRepo = visualTestRepo;
    }

    @GetMapping("/numberTest/topHudred")
    public List<NumberTest> getTopHundredNumberTestResults(){
        List<NumberTest> list =  numberTestRepo.findBestScores();
        return list;
    }

    @GetMapping("/numberTest/graphValid")
    public List<NumberTest> getGraphValidScoresNumberTestResults(){
        List<NumberTest> list =  numberTestRepo.findAllGraphValidScores();
        return list;
    }

    @GetMapping("/sequenceTest/topHudred")
    public List<SequenceTest> getTopHundredSequenceTestResults(){
        List<SequenceTest> list =  sequenceTestRepo.findBestScores();
        return list;
    }

    @GetMapping("/sequenceTest/graphValid")
    public List<SequenceTest> getGraphValidScoresSequenceTestResults(){
        List<SequenceTest> list =  sequenceTestRepo.findAllGraphValidScores();
        return list;
    }

    @GetMapping("/verbalTest/topHudred")
    public List<VerbalTest> getTopHundredVerbalTestResults(){
        List<VerbalTest> list =  verbalTestRepo.findBestScores();
        return list;
    }

    @GetMapping("/verbalTest/graphValid")
    public List<VerbalTest> getGraphValidScoresVerbalTestResults(){
        List<VerbalTest> list =  verbalTestRepo.findAllGraphValidScores();
        return list;
    }

    @GetMapping("/visualTest/topHudred")
    public List<VisualTest> getTopHundredVisualTestResults(){
        List<VisualTest> list =  visualTestRepo.findBestScores();
        return list;
    }

    @GetMapping("/visualTest/graphValid")
    public List<VisualTest> getGraphValidScoresVisualTestResults(){
        List<VisualTest> list =  visualTestRepo.findAllGraphValidScores();
        return list;
    }

    @GetMapping("/personalPerformance")
    public List<String> getPersonalPerformance(){
        List<String> list = new ArrayList<>();
        //Pobieranie aktualnego uzytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

        //pobieranie najlepszego wyniku uzytkownika
        NumberTest number =  numberTestRepo.findPersonalBest(currentUser.getId());
        SequenceTest sequence =  sequenceTestRepo.findPersonalBest(currentUser.getId());
        VerbalTest verbal =  verbalTestRepo.findPersonalBest(currentUser.getId());
        VisualTest visual =  visualTestRepo.findPersonalBest(currentUser.getId());

        //pobieranie list wszystkich uzytkownikow do porownania
        List<NumberTest> numberList =  numberTestRepo.findAllGraphValidScores();
        List<SequenceTest> sequenceList =  sequenceTestRepo.findAllGraphValidScores();
        List<VerbalTest> verbalList =  verbalTestRepo.findAllGraphValidScores();
        List<VisualTest> visualList =  visualTestRepo.findAllGraphValidScores();

        //zmienne przechowujace srednie wyniki testow
        float numberAverage = 0;
        float sequenceAverage = 0;
        float verbalAverage = 0;
        float visualAverage = 0;

        float numberPercentile = 0;
        float sequencePercentile = 0;
        float verbalPercentile = 0;
        float visualPercentile = 0;

        for(NumberTest numberTest: numberList){
            numberAverage += numberTest.getScore();
            if(numberTest.getScore() <= number.getScore()){
                numberPercentile += 1;
            }
        }
        numberAverage /= numberList.size();
        numberPercentile /= numberList.size();

        for(SequenceTest sequenceTest: sequenceList){
            sequenceAverage += sequenceTest.getScore();
            if(sequenceTest.getScore() <= sequence.getScore()){
                sequencePercentile += 1;
            }
        }
        sequenceAverage /= sequenceList.size();
        sequencePercentile /= sequenceList.size();

        for(VerbalTest verbalTest: verbalList){
            verbalAverage += verbalTest.getScore();
            if(verbalTest.getScore() <= verbal.getScore()){
                verbalPercentile += 1;
            }
        }
        verbalAverage /= verbalList.size();
        verbalPercentile /= verbalList.size();

        for(VisualTest visualTest: visualList){
            visualAverage += visualTest.getScore();
            if(visualTest.getScore() <= visual.getScore()){
                visualPercentile += 1;
            }
        }
        visualAverage /= visualList.size();
        visualPercentile /= visualList.size();

        list.add("test name: \'number memory\',\npersonalBest: \'" + number.getScore() + "\'.\naverageScore: \'" + numberAverage + "\'.\nPercentile: \'" + numberPercentile + "%\'");
        list.add("test name: \'sequence memory\',\npersonalBest: \'" + sequence.getScore() + "\'.\naverageScore: \'" + sequenceAverage + "\'.\nPercentile: \'" + sequencePercentile + "%\'");
        list.add("test name: \'verbal memory\',\npersonalBest: \'" + verbal.getScore() + "\'.\naverageScore: \'" + verbalAverage + "\'.\nPercentile: \'" + verbalPercentile + "%\'");
        list.add("test name: \'visual memory\',\npersonalBest: \'" + visual.getScore() + "\'.\naverageScore: \'" + visualAverage + "\'.\nPercentile: \'" + visualPercentile + "%\'");

        return list;
    }




}

