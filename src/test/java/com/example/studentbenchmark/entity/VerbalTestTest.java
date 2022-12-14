package com.example.studentbenchmark.entity;

import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class VerbalTestTest {

    @Test
    public void shouldCreateVerbalTestWithCorrectData() {
        Date testingDate = new Date();
        int testingScore = 1;
        long testingIdTest = 2;
        long testingIdUser = 3;
        VerbalTest verbal = new VerbalTest(testingScore, testingIdTest, testingIdUser, testingDate);

        assertAll("Test should have correct data",
                () -> assertEquals(verbal.getScore(), testingScore),
                () -> assertEquals(verbal.getIdTest(), testingIdTest),
                () -> assertEquals(verbal.getIdUser(), testingIdUser),
                () -> assertEquals(verbal.getDateOfSubmission(), testingDate));
    }

    @Test
    public void isScoreValidFunctionShouldReturnTrueIfCorrectScore() {
        VerbalTest verbal1 = new VerbalTest(0);
        VerbalTest verbal2 = new VerbalTest(10);
        VerbalTest verbal3 = new VerbalTest(1000);

        assertAll("Score should be between 0 and 1000",
                () -> assertTrue(verbal1.isScoreValid()),
                () -> assertTrue(verbal2.isScoreValid()),
                () -> assertTrue(verbal3.isScoreValid()));
    }

    @Test
    public void isScoreValidFunctionShouldReturnFalseIfIncorrectScore() {
        VerbalTest verbal1 = new VerbalTest(-1);
        VerbalTest verbal2 = new VerbalTest(1001);

        assertAll("Score can't be smaller then 0 or bigger then 1000",
                () -> assertFalse(verbal1.isScoreValid()),
                () -> assertFalse(verbal2.isScoreValid()));
    }

}
