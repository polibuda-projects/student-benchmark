package com.example.studentbenchmark.repository;


import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

@SpringBootTest

public class VerbalTestRepoTest {

    @Autowired
    private VerbalTestRepo verbalTestRepo;

    @BeforeEach
    void setUp() {
        verbalTestRepo = Mockito.mock(VerbalTestRepo.class);
    }

    @Test
    public void shouldNotHaveNullInFunctionFindAllGraphValidScores() {
        List<VerbalTest> verbalTestList = verbalTestRepo.findAllGraphValidScores();
        for (VerbalTest verbalTest: verbalTestList) {
            assertNotNull(verbalTest.getIdTest());
            assertNotNull(verbalTest.getIdUser());
            assertNotNull(verbalTest.getScore());
            assertNotNull(verbalTest.getDateOfSubmission());
        }
    }

    @Test
    public void shouldNotHaveNullInFunctionFindBestScores() {
        List<VerbalTest> verbalTestList = verbalTestRepo.findBestScores();
        for (VerbalTest verbalTest: verbalTestList) {
            assertNotNull(verbalTest.getIdTest());
            assertNotNull(verbalTest.getIdUser());
            assertNotNull(verbalTest.getScore());
            assertNotNull(verbalTest.getDateOfSubmission());
        }
    }

    @Test
    public void shouldNotHaveNullInFunctionGetAllScores() {
        List<VerbalTest> verbalTestList = verbalTestRepo.getAllScores();
        for (VerbalTest verbalTest: verbalTestList) {
            assertNotNull(verbalTest.getIdTest());
            assertNotNull(verbalTest.getIdUser());
            assertNotNull(verbalTest.getScore());
            assertNotNull(verbalTest.getDateOfSubmission());
        }
    }

}
