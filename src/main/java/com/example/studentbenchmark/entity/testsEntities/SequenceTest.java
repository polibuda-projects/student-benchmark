package com.example.studentbenchmark.entity.testsEntities;

import javax.persistence.Entity;

@Entity(name = "sequence_test")
public final class SequenceTest extends AppTest {

    public static final int MIN_VALID_SCORE = 0;
    public static final int MAX_VALID_SCORE = 30;

    @Override
    public boolean isScoreValid() {
        return score >= MIN_VALID_SCORE && score <= MAX_VALID_SCORE;
    }

}
