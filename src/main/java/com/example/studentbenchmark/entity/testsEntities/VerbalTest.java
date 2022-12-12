package com.example.studentbenchmark.entity.testsEntities;

import javax.persistence.Entity;

@Entity(name = "verbal_test")
public final class VerbalTest extends AppTest {

    public static final int MIN_VALID_SCORE = 0;
    public static final int MAX_VALID_SCORE = 1000;
    public static final int MAX_VALID_SCORE_GRAPH = 200;

    @Override
    public boolean isScoreValid() {
        return score >= MIN_VALID_SCORE && score <= MAX_VALID_SCORE;
    }

}

