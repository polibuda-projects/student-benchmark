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
        NumberTest number = numberTestRepo.findPersonalBest(currentUser.getId());
        SequenceTest sequence = sequenceTestRepo.findPersonalBest(currentUser.getId());
        VerbalTest verbal = verbalTestRepo.findPersonalBest(currentUser.getId());
        VisualTest visual = visualTestRepo.findPersonalBest(currentUser.getId());

        //pobieranie list wszystkich uzytkownikow do porownania
        List<NumberTest> numberList = numberTestRepo.findAllGraphValidScores();
        List<SequenceTest> sequenceList = sequenceTestRepo.findAllGraphValidScores();
        List<VerbalTest> verbalList = verbalTestRepo.findAllGraphValidScores();
        List<VisualTest> visualList = visualTestRepo.findAllGraphValidScores();

        //zmienne przechowujace srednie wyniki testow
        float numberAverage = 0;
        float sequenceAverage = 0;
        float verbalAverage = 0;
        float visualAverage = 0;

        float numberPercentile = 0;
        float sequencePercentile = 0;
        float verbalPercentile = 0;
        float visualPercentile = 0;

        for (NumberTest numberTest : numberList) {
            numberAverage += numberTest.getScore();
            if (numberTest.getScore() <= number.getScore()) {
                numberPercentile += 1;
            }
        }
        numberAverage /= numberList.size();
        numberPercentile /= numberList.size();

        for (SequenceTest sequenceTest : sequenceList) {
            sequenceAverage += sequenceTest.getScore();
            if (sequenceTest.getScore() <= sequence.getScore()) {
                sequencePercentile += 1;
            }
        }
        sequenceAverage /= sequenceList.size();
        sequencePercentile /= sequenceList.size();

        for (VerbalTest verbalTest : verbalList) {
            verbalAverage += verbalTest.getScore();
            if (verbalTest.getScore() <= verbal.getScore()) {
                verbalPercentile += 1;
            }
        }
        verbalAverage /= verbalList.size();
        verbalPercentile /= verbalList.size();

        for (VisualTest visualTest : visualList) {
            visualAverage += visualTest.getScore();
            if (visualTest.getScore() <= visual.getScore()) {
                visualPercentile += 1;
            }
        }
        visualAverage /= visualList.size();
        visualPercentile /= visualList.size();

        data.add(new PersonalData("number memory", number.getScore(), numberAverage, (int) (100 * numberPercentile)));
        data.add(new PersonalData("sequence memory", sequence.getScore(), sequenceAverage, (int) (100 * sequencePercentile)));
        data.add(new PersonalData("verbal memory", verbal.getScore(), verbalAverage, (int) (100 * verbalPercentile)));
        data.add(new PersonalData("visual memory", visual.getScore(), visualAverage, (int) (100 * visualPercentile)));

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

