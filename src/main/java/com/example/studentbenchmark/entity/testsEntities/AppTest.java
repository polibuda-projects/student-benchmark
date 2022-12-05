package com.example.studentbenchmark.entity.testsEntities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
public abstract class AppTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTest;
    private Long idUser;
    private Date dateOfSubmission;
    protected final int score;

    public AppTest() {
        this.score = 0;
    }

    public AppTest(int score) {
        this.score = score;
    }

    public abstract boolean isScoreValid();

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
