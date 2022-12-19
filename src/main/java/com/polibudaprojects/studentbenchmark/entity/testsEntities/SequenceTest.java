package com.polibudaprojects.studentbenchmark.entity.testsEntities;

import javax.persistence.Entity;
import java.util.Date;


@Entity(name = "sequence_test")
public final class SequenceTest extends AppTest {
    public SequenceTest() {
    }

    public SequenceTest(int score, Long idTest, Long idUser, Date dateOfSubmission) {
        super(score, idTest, idUser, dateOfSubmission);
    }

    public SequenceTest(int score) {
        super(score);
    }
}
