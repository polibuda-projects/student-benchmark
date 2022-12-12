package com.example.studentbenchmark.controllers;

import static java.awt.geom.Path2D.contains;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.studentbenchmark.entity.testsEntities.NumberTest;
import com.example.studentbenchmark.entity.testsEntities.SequenceTest;
import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import com.example.studentbenchmark.entity.testsEntities.VisualTest;
import com.example.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DashboardControllerTest<T> {
    // Wstrzyknij tutaj potrzebne zależności
    private NumberTestRepo numberTestRepo;
    private SequenceTestRepo sequenceTestRepo;
    private VerbalTestRepo verbalTestRepo;
    private VisualTestRepo visualTestRepo;


    @Autowired
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        // Tutaj możesz wstawić kod, który wykona się przed każdym testem,
        // np. ustawienie wartości pól klasowych
    }


        @Test
        public void testGetDashboardPublicData() {
            // given
            // Wygenerowanie przykładowych danych testowych
            List<NumberTest> numberBestList = numberTestRepo.findBestScores();
            List<SequenceTest> sequenceBestList =  sequenceTestRepo.findBestScores();
            List<VerbalTest> verbalBestList = verbalTestRepo.findBestScores();
            List<VisualTest> visualBestList = visualTestRepo.findBestScores();

            List<NumberTest> numberGraphList = numberTestRepo.findAllGraphValidScores();
            List<SequenceTest> sequenceGraphList = sequenceTestRepo.findAllGraphValidScores();
            List<VerbalTest> verbalGraphList = verbalTestRepo.findAllGraphValidScores();
            List<VisualTest> visualGraphList = visualTestRepo.findAllGraphValidScores();

            // Ustawienie mocków repository
            when(numberTestRepo.findBestScores()).thenReturn(numberBestList);
            when(sequenceTestRepo.findBestScores()).thenReturn(sequenceBestList);
            when(verbalTestRepo.findBestScores()).thenReturn(verbalBestList);
            when(visualTestRepo.findBestScores()).thenReturn(visualBestList);

            when(numberTestRepo.findAllGraphValidScores()).thenReturn(numberGraphList);
            when(sequenceTestRepo.findAllGraphValidScores()).thenReturn(sequenceGraphList);
            when(verbalTestRepo.findAllGraphValidScores()).thenReturn(verbalGraphList);
            when(visualTestRepo.findAllGraphValidScores()).thenReturn(visualGraphList);

            // when
            String result = dashboardController.getDashboardPublicData();

            // then
            // Sprawdzenie czy zwracany obiekt jest listą
            //assertThat(result, instanceOf(List.class));
            assertEquals(result, "dashboard");

            // Sprawdzenie czy lista zawiera poprawne dane
            //assertThat(result, contains(numberBestList, sequenceBestList, verbalBestList, visualBestList, numberGraphList, sequenceGraphList, verbalGraphList, visualGraphList));
        }


    @Test
    void testGetDashboardPersonalData() {
        // Tutaj możesz napisać test dla metody getDashboardPersonalData()
        // wykorzystując przy tym zależności wstrzykiwane do klasy

    }
}
