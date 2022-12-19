package com.polibudaprojects.studentbenchmark.controllers;

import com.google.gson.Gson;
import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.testsEntities.*;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@Service
public class DashboardController {
    private final NumberTestRepo numberTestRepo;
    private final SequenceTestRepo sequenceTestRepo;
    private final VerbalTestRepo verbalTestRepo;
    private final VisualTestRepo visualTestRepo;
    private final UserRepo userRepo;

    @Autowired
    DashboardController(NumberTestRepo numberTestRepo, SequenceTestRepo sequenceTestRepo, VerbalTestRepo verbalTestRepo,
                        VisualTestRepo visualTestRepo, UserRepo userRepo) {
        this.numberTestRepo = numberTestRepo;
        this.sequenceTestRepo = sequenceTestRepo;
        this.verbalTestRepo = verbalTestRepo;
        this.visualTestRepo = visualTestRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/DashboardPublic")
    public String getDashboardPublicData() {
        List<List<PublicData>> data = new ArrayList<>();
        Gson gson = new Gson();

        data.add(top100PublicDataFrom(numberTestRepo.findBestScores()));
        data.add(top100PublicDataFrom(sequenceTestRepo.findBestScores()));
        data.add(top100PublicDataFrom(verbalTestRepo.findBestScores()));
        data.add(top100PublicDataFrom(visualTestRepo.findBestScores()));

        data.add(graphValidPublicDataFrom(numberTestRepo.findAllGraphValidScores()));
        data.add(graphValidPublicDataFrom(sequenceTestRepo.findAllGraphValidScores()));
        data.add(graphValidPublicDataFrom(verbalTestRepo.findAllGraphValidScores()));
        data.add(graphValidPublicDataFrom(visualTestRepo.findAllGraphValidScores()));

        return gson.toJson(data);
    }

    private <T extends AppTest> List<PublicData> top100PublicDataFrom(List<T> bestScoresTests) {
        List<PublicData> top100 = new ArrayList<>();
        for (AppTest test : bestScoresTests) {
            AppUser user = userRepo.findByID(test.getIdUser());
            top100.add(new PublicData(user.getNickname(), test.getDateOfSubmission(), test.getScore()));
        }
        return top100;
    }

    private <T extends AppTest> List<PublicData> graphValidPublicDataFrom(List<T> graphValidTests) {
        List<PublicData> graphValid = new ArrayList<>();
        String name = "";
        for (AppTest test : graphValidTests) {
            if (test.getIdUser() != 0) {
                AppUser user = userRepo.findByID(test.getIdUser());
                name = user.getNickname();
            }
            graphValid.add(new PublicData(name, test.getDateOfSubmission(), test.getScore()));
        }
        return graphValid;
    }

    @GetMapping("/DashboardPersonal")
    public String getDashboardPersonalData() {
        Gson gson = new Gson();
        List<PersonalData> data = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserEntityDetails currentUser = (AppUserEntityDetails) authentication.getPrincipal();

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

        if (userNumberList.size() > 0) existNumber = true;
        if (userSequenceList.size() > 0) existSequence = true;
        if (userVerbalList.size() > 0) existVerbal = true;
        if (userVisualList.size() > 0) existVisual = true;

        NumberTest number = new NumberTest();
        SequenceTest sequence = new SequenceTest();
        VerbalTest verbal = new VerbalTest();
        VisualTest visual = new VisualTest();

        if (existNumber) {
            number = userNumberList.get(0);
        }
        if (existSequence) {
            sequence = userSequenceList.get(0);
        }
        if (existVerbal) {
            verbal = userVerbalList.get(0);
        }
        if (existVisual) {
            visual = userVisualList.get(0);
        }

        List<NumberTest> numberList = numberTestRepo.getAllScores();
        List<SequenceTest> sequenceList = sequenceTestRepo.getAllScores();
        List<VerbalTest> verbalList = verbalTestRepo.getAllScores();
        List<VisualTest> visualList = visualTestRepo.getAllScores();

        float numberAverage = 0;
        float sequenceAverage = 0;
        float verbalAverage = 0;
        float visualAverage = 0;

        float numberPercentile = 0;
        float sequencePercentile = 0;
        float verbalPercentile = 0;
        float visualPercentile = 0;

        if (existNumber) {
            for (NumberTest numberTest : numberList) {
                if (numberTest.getScore() <= number.getScore()) {
                    numberPercentile += 1;
                }
            }
            for (NumberTest numberTest : allUserNumberList) {
                numberAverage += numberTest.getScore();
            }
            numberAverage /= allUserNumberList.size();
            numberPercentile /= numberList.size();
        }

        if (existSequence) {
            for (SequenceTest sequenceTest : sequenceList) {
                if (sequenceTest.getScore() <= sequence.getScore()) {
                    sequencePercentile += 1;
                }
            }
            for (SequenceTest sequenceTest : allUserSequenceList) {
                sequenceAverage += sequenceTest.getScore();
            }
            sequenceAverage /= allUserSequenceList.size();
            sequencePercentile /= sequenceList.size();
        }

        if (existVerbal) {
            for (VerbalTest verbalTest : verbalList) {
                if (verbalTest.getScore() <= verbal.getScore()) {
                    verbalPercentile += 1;
                }
            }
            for (VerbalTest verbalTest : allUserVerbalList) {
                verbalAverage += verbalTest.getScore();
            }
            verbalAverage /= allUserVerbalList.size();
            verbalPercentile /= verbalList.size();
        }

        if (existVisual) {
            for (VisualTest visualTest : visualList) {
                if (visualTest.getScore() <= visual.getScore()) {
                    visualPercentile += 1;
                }
            }
            for (VisualTest visualTest : allUserVisualList) {
                visualAverage += visualTest.getScore();
            }
            visualAverage /= allUserVisualList.size();
            visualPercentile /= visualList.size();
        }

        if (existNumber) {
            data.add(new PersonalData("number memory", number.getScore(), numberAverage, (int) (100 * numberPercentile)));
        } else {
            data.add(new PersonalData("number memory", 0, 0, 0));
        }
        if (existSequence) {
            data.add(new PersonalData("sequence memory", sequence.getScore(), sequenceAverage, (int) (100 * sequencePercentile)));
        } else {
            data.add(new PersonalData("sequence memory", 0, 0, 0));
        }
        if (existVerbal) {
            data.add(new PersonalData("verbal memory", verbal.getScore(), verbalAverage, (int) (100 * verbalPercentile)));
        } else {
            data.add(new PersonalData("verbal memory", 0, 0, 0));
        }
        if (existVisual) {
            data.add(new PersonalData("visual memory", visual.getScore(), visualAverage, (int) (100 * visualPercentile)));
        } else {
            data.add(new PersonalData("visual memory", 0, 0, 0));
        }

        return gson.toJson(data);
    }

    private record PersonalData(String testName, int personalBest, float averageScore, int percentile) {
    }

    private record PublicData(String nickname, Date dateOfSubmission, int score) {
    }
}
