package com.polibudaprojects.studentbenchmark.entity.testsEntities;

import javax.persistence.Entity;
import java.util.Date;


@Entity(name = "number_test")
public final class NumberTest extends AppTest {
    public NumberTest() {
    }

    public NumberTest(int score, Long idTest, Long idUser, Date dateOfSubmission) {
        super(score, idTest, idUser, dateOfSubmission);
    }

    public NumberTest(int score) {
        super(score);
    }
}
