package com.polibudaprojects.studentbenchmark.entity.testsEntities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import java.util.Date;


@Entity(name = "verbal_test")
@Access(AccessType.FIELD)
public final class VerbalTest extends AppTest {

    public static final int MIN_VALID_SCORE = 0;
    public static final int MAX_VALID_SCORE = 1000;
    public static final int MAX_VALID_SCORE_GRAPH = 200;

    @Override
    public boolean isScoreValid() {
        return score >= MIN_VALID_SCORE && score <= MAX_VALID_SCORE;
    }

    public VerbalTest() {

    }

    public VerbalTest(int score, Long idTest, Long idUser, Date dateOfSubmission) {
        super(score, idTest, idUser, dateOfSubmission);
    }

    public VerbalTest(int score) {
        super(score);
    }

}

