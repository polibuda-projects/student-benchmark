package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.testsEntities.*;
import com.example.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import com.google.gson.Gson;
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
public class DashboardController<T extends AppTest> {
    private final NumberTestRepo numberTestRepo;
    private final SequenceTestRepo sequenceTestRepo;
    private final VerbalTestRepo verbalTestRepo;
    private final VisualTestRepo visualTestRepo;

    @Autowired
    DashboardController(NumberTestRepo numberTestRepo, SequenceTestRepo sequenceTestRepo, VerbalTestRepo verbalTestRepo, VisualTestRepo visualTestRepo) {
        this.numberTestRepo = numberTestRepo;
        this.sequenceTestRepo = sequenceTestRepo;
        this.verbalTestRepo = verbalTestRepo;
        this.visualTestRepo = visualTestRepo;
    }

    //Funkcja zwraca dane w formacie JSON zawierajace top 100 kazdego z testow a nastapnie dane do tworzenia wykresow
    @GetMapping("/DashboardPublic")
    public String getDashboardPublicData() {
        List<List<T>> data = new ArrayList<>();
        Gson gson = new Gson();

        List<NumberTest> numberBestList = numberTestRepo.findBestScores();
        List<SequenceTest> sequenceBestList = sequenceTestRepo.findBestScores();
        List<VerbalTest> verbalBestList = verbalTestRepo.findBestScores();
        List<VisualTest> visualBestList = visualTestRepo.findBestScores();

        List<NumberTest> numberGraphList = numberTestRepo.findAllGraphValidScores();
        List<SequenceTest> sequenceGraphList = sequenceTestRepo.findAllGraphValidScores();
        List<VerbalTest> verbalGraphList = verbalTestRepo.findAllGraphValidScores();
        List<VisualTest> visualGraphList = visualTestRepo.findAllGraphValidScores();

        data.add((List<T>) numberBestList);
        data.add((List<T>) sequenceBestList);
        data.add((List<T>) verbalBestList);
        data.add((List<T>) visualBestList);

        data.add((List<T>) numberGraphList);
        data.add((List<T>) sequenceGraphList);
        data.add((List<T>) verbalGraphList);
        data.add((List<T>) visualGraphList);

        return gson.toJson(data);
    }


    //Funkcja zwraca dane w formacie JSON zawierajace: nazwe testu, najlepszy wynik zalogowanego, sredni wynik tego testu, percentyl
    @GetMapping("/DashboardPersonal")
    public String getDashboardPersonalData() {
        Gson gson = new Gson();
        List<PersonalData> data = new ArrayList<>();

        //Pobieranie aktualnego uzytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();


        //pobieranie najlepszego wyniku uzytkownika
        List<NumberTest> userNumberList = numberTestRepo.findPersonal(currentUser.getId());
        List<SequenceTest> userSequenceList = sequenceTestRepo.findPersonal(currentUser.getId());
        List<VerbalTest> userVerbalList = verbalTestRepo.findPersonal(currentUser.getId());
        List<VisualTest> userVisualList = visualTestRepo.findPersonal(currentUser.getId());

        List<NumberTest> allUserNumberList = numberTestRepo.findAllPersonal(currentUser.getId());
        List<SequenceTest> allUserSequenceList = sequenceTestRepo.findAllPersonal(currentUser.getId());
        List<VerbalTest> allUserVerbalList = verbalTestRepo.findAllPersonal(currentUser.getId());
        List<VisualTest> allUserVisualList = visualTestRepo.findAllPersonal(currentUser.getId());

        boolean existNumber = false;
        boolean existSequence = false;
        boolean existVerbal = false;
        boolean existVisual = false;

        if(userNumberList.size()>0) existNumber = true;
        if(userSequenceList.size()>0) existSequence = true;
        if(userVerbalList.size()>0) existVerbal = true;
        if(userVisualList.size()>0) existVisual = true;

        NumberTest number = new NumberTest();
        SequenceTest sequence = new SequenceTest();
        VerbalTest verbal = new VerbalTest();
        VisualTest visual = new VisualTest();

        if(existNumber) {
            number = userNumberList.get(0);
        }
        if(existSequence){
            sequence = userSequenceList.get(0);
        }
        if(existVerbal){
            verbal = userVerbalList.get(0);
        }
        if(existVisual){
            visual = userVisualList.get(0);
        }


        //pobieranie list wszystkich uzytkownikow do porownania
        List<NumberTest> numberList = numberTestRepo.getAllScores();
        List<SequenceTest> sequenceList = sequenceTestRepo.getAllScores();
        List<VerbalTest> verbalList = verbalTestRepo.getAllScores();
        List<VisualTest> visualList = visualTestRepo.getAllScores();



        //zmienne przechowujace srednie wyniki testow
        float numberAverage = 0;
        float sequenceAverage = 0;
        float verbalAverage = 0;
        float visualAverage = 0;

        float numberPercentile = 0;
        float sequencePercentile = 0;
        float verbalPercentile = 0;
        float visualPercentile = 0;

        if(existNumber) {
            for (NumberTest numberTest : numberList) {
                if (numberTest.getScore() <= number.getScore()) {
                    numberPercentile += 1;
                }
            }
            for (NumberTest numberTest : allUserNumberList){
                numberAverage += numberTest.getScore();
            }
            numberAverage /= allUserNumberList.size();
            numberPercentile /= numberList.size();
        }

        if(existSequence) {
            for (SequenceTest sequenceTest : sequenceList) {
                if (sequenceTest.getScore() <= sequence.getScore()) {
                    sequencePercentile += 1;
                }
            }
            for(SequenceTest sequenceTest : allUserSequenceList){
                sequenceAverage += sequenceTest.getScore();
            }
            sequenceAverage /= allUserSequenceList.size();
            sequencePercentile /= sequenceList.size();
        }


        if(existVerbal) {
            for (VerbalTest verbalTest : verbalList) {
                if (verbalTest.getScore() <= verbal.getScore()) {
                    verbalPercentile += 1;
                }
            }
            for(VerbalTest verbalTest : allUserVerbalList){
                verbalAverage += verbalTest.getScore();
            }
            verbalAverage /= allUserVerbalList.size();
            verbalPercentile /= verbalList.size();
        }

        if(existVisual) {
            for (VisualTest visualTest : visualList) {
                if (visualTest.getScore() <= visual.getScore()) {
                    visualPercentile += 1;
                }
            }
            for(VisualTest visualTest : allUserVisualList) {
                visualAverage += visualTest.getScore();
            }
            visualAverage /= allUserVisualList.size();
            visualPercentile /= visualList.size();
        }



        if(existNumber){
            data.add(new PersonalData("number memory", number.getScore(), numberAverage, (int) (100 * numberPercentile)));
        }
        else{
            data.add(new PersonalData("number memory", 0, 0, 0));
        }
        if(existSequence){
            data.add(new PersonalData("sequence memory", sequence.getScore(), sequenceAverage, (int) (100 * sequencePercentile)));
        }
        else{
            data.add(new PersonalData("sequance memory", 0, 0, 0));
        }
        if(existVerbal){
            data.add(new PersonalData("verbal memory", verbal.getScore(), verbalAverage, (int) (100 * verbalPercentile)));
        }
        else{
            data.add(new PersonalData("verbal memory", 0, 0, 0));
        }
        if(existVisual){
            data.add(new PersonalData("visual memory", visual.getScore(), visualAverage, (int) (100 * visualPercentile)));
        }
        else{
            data.add(new PersonalData("visual memory", 0, 0, 0));
        }

        return gson.toJson(data);
    }

    private class PersonalData {
        private final String testName;
        private final int personalBest;
        private final float averageScore;
        private final int percentile;

        PersonalData(String testName, int personalBest, float averageScore, int percentile) {
            this.testName = testName;
            this.personalBest = personalBest;
            this.averageScore = averageScore;
            this.percentile = percentile;
        }
    }

}

