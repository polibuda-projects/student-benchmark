package com.example.studentbenchmark.entity.testsEntities;

import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "visual_test")
public final class VisualTest extends AppTest {
    public VisualTest() {

    }

    public VisualTest(int score, Long idTest, Long idUser, Date dateOfSubmission) {
        super(score, idTest, idUser, dateOfSubmission);
    }

    public VisualTest(int score) {
        super(score);
    }
}

