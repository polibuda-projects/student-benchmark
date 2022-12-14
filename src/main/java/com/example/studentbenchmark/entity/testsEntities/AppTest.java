package com.example.studentbenchmark.entity.testsEntities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
public abstract class AppTest {

    public static final int MIN_VALID_SCORE = 0;
    public static final int MAX_VALID_SCORE = 100;
    public static final int MAX_VALID_SCORE_GRAPH = 30;
    protected final int score;

    public AppTest(int score, Long idTest, Long idUser, Date dateOfSubmission) {
        this.score = score;
        this.idTest = idTest;
        this.idUser = idUser;
        this.dateOfSubmission = dateOfSubmission;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTest;
    private Long idUser;
    private Date dateOfSubmission;

    public AppTest() {
        this.score = 0;
    }

    public AppTest(int score) {
        this.score = score;
    }

    public boolean isScoreValid() {
        return score >= MIN_VALID_SCORE && score <= MAX_VALID_SCORE;
    }

    public int getScore() {
        return score;
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }
}

