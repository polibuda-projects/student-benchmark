package com.polibudaprojects.studentbenchmark.controllers;

import static com.polibudaprojects.studentbenchmark.entity.AppUser.Role.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.testsEntities.NumberTest;
import com.polibudaprojects.studentbenchmark.entity.testsEntities.SequenceTest;
import com.polibudaprojects.studentbenchmark.entity.testsEntities.VerbalTest;
import com.polibudaprojects.studentbenchmark.entity.testsEntities.VisualTest;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.polibudaprojects.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import java.util.List;

@SpringBootTest
class DashboardControllerTest {
    @Autowired
    private NumberTestRepo numberTestRepo;
    @Autowired
    private SequenceTestRepo sequenceTestRepo;
    @Autowired
    private VerbalTestRepo verbalTestRepo;
    @Autowired
    private VisualTestRepo visualTestRepo;
    private Authentication authentication;


    @BeforeEach
    void setUp(){
        numberTestRepo = Mockito.mock(NumberTestRepo.class);
        sequenceTestRepo = Mockito.mock(SequenceTestRepo.class);
        verbalTestRepo = Mockito.mock(VerbalTestRepo.class);
        visualTestRepo = Mockito.mock(VisualTestRepo.class);
        authentication = Mockito.mock(Authentication.class);
    }

    @Test
    void testNumberTest_ForPublicData() {

        List<NumberTest> numberBestList = numberTestRepo.findBestScores();
        when(numberTestRepo.findBestScores()).thenReturn(numberBestList);

        List<NumberTest> numberGraphList = numberTestRepo.findAllGraphValidScores();
        when(numberTestRepo.findAllGraphValidScores()).thenReturn(numberGraphList);

        assertAll("Should return",
                () -> assertThat(numberBestList).isInstanceOf(List.class),
                () -> assertThat(numberGraphList).isInstanceOf(List.class),
                () -> assertEquals(numberBestList, numberGraphList),
                () -> assertTrue(numberBestList.containsAll(numberGraphList)));
    }

    @Test
    void testSequenceTest_ForPublicData() {

        List<SequenceTest> sequenceBestList = sequenceTestRepo.findBestScores();
        when(sequenceTestRepo.findBestScores()).thenReturn(sequenceBestList);

        List<SequenceTest> sequenceGraphList = sequenceTestRepo.findAllGraphValidScores();
        when(sequenceTestRepo.findAllGraphValidScores()).thenReturn(sequenceGraphList);

        assertAll("Should return",
                () -> assertThat(sequenceBestList).isInstanceOf(List.class),
                () -> assertThat(sequenceGraphList).isInstanceOf(List.class),
                () -> assertEquals(sequenceBestList, sequenceGraphList),
                () -> assertTrue(sequenceBestList.containsAll(sequenceGraphList)));
    }

    @Test
    void testVerbalTest_ForPublicData() {

        List<VerbalTest> verbalBestList = verbalTestRepo.findBestScores();
        when(verbalTestRepo.findBestScores()).thenReturn(verbalBestList);

        List<VerbalTest> verbalGraphList = verbalTestRepo.findAllGraphValidScores();
        when(verbalTestRepo.findAllGraphValidScores()).thenReturn(verbalGraphList);

        assertAll("Should return",
                () -> assertThat(verbalBestList).isInstanceOf(List.class),
                () -> assertThat(verbalGraphList).isInstanceOf(List.class),
                () -> assertEquals(verbalBestList, verbalGraphList),
                () -> assertTrue(verbalBestList.containsAll(verbalGraphList)));
    }

    @Test
    void testVisualTest_ForPublicData() {

        List<VisualTest> visualBestList = visualTestRepo.findBestScores();
        when(visualTestRepo.findBestScores()).thenReturn(visualBestList);

        List<VisualTest> visualGraphList = visualTestRepo.findAllGraphValidScores();
        when(visualTestRepo.findAllGraphValidScores()).thenReturn(visualGraphList);

        assertAll("Should return",
                () -> assertThat(visualBestList).isInstanceOf(List.class),
                () -> assertThat(visualGraphList).isInstanceOf(List.class),
                () -> assertEquals(visualBestList, visualGraphList),
                () -> assertTrue(visualBestList.containsAll(visualGraphList)));
    }

    @Test
    void testNumberTest_ForPersonalData(){
        Gson gson = new Gson();
        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("user", "user@email.com", "user1", USER));
        when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(numberTestRepo.findPersonalBest(currentUser.getId())).thenReturn(new NumberTest());
        String expected = "{\"testName\":\"number memory\",\"personalBest\":15,\"averageScore\":10.0,\"percentile\":75}";
        PersonalData personalData = new PersonalData("number memory", 15, 10.0f, 75);
        String actual = gson.toJson(personalData);
        assertThat(numberTestRepo.findPersonalBest(currentUser.getId())).isInstanceOf(NumberTest.class);
        assertEquals(expected, actual);

    }
    @Test
    void testSequenceTest_ForPersonalData(){
        Gson gson = new Gson();
        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("user", "user@email.com", "user1", USER));
        when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(sequenceTestRepo.findPersonalBest(currentUser.getId())).thenReturn(new SequenceTest());
        String expected = "{\"testName\":\"sequence memory\",\"personalBest\":15,\"averageScore\":10.0,\"percentile\":75}";
        PersonalData personalData = new PersonalData("sequence memory", 15, 10.0f, 75);
        String actual = gson.toJson(personalData);
        assertThat(sequenceTestRepo.findPersonalBest(currentUser.getId())).isInstanceOf(SequenceTest.class);
        assertEquals(expected, actual);

    }
    @Test
    void testVerbalTest_ForPersonalData(){
        Gson gson = new Gson();
        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("user", "user@email.com", "user1", USER));
        when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(verbalTestRepo.findPersonalBest(currentUser.getId())).thenReturn(new VerbalTest());
        String expected = "{\"testName\":\"verbal memory\",\"personalBest\":15,\"averageScore\":10.0,\"percentile\":75}";
        PersonalData personalData = new PersonalData("verbal memory", 15, 10.0f, 75);
        String actual = gson.toJson(personalData);
        assertThat(verbalTestRepo.findPersonalBest(currentUser.getId())).isInstanceOf(VerbalTest.class);
        assertEquals(expected, actual);
    }

    @Test
    void testVisualTest_ForPersonalData(){
        Gson gson = new Gson();
        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("user", "user@email.com", "user1", USER));
        when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(visualTestRepo.findPersonalBest(currentUser.getId())).thenReturn(new VisualTest());
        String expected = "{\"testName\":\"visual memory\",\"personalBest\":15,\"averageScore\":10.0,\"percentile\":75}";
        PersonalData personalData = new PersonalData("visual memory", 15, 10.0f, 75);
        String actual = gson.toJson(personalData);
        assertThat(visualTestRepo.findPersonalBest(currentUser.getId())).isInstanceOf(VisualTest.class);
        assertEquals(expected, actual);
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
