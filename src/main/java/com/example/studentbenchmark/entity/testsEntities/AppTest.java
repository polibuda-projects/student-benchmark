package com.example.studentbenchmark.entity.testsEntities;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
public class AppTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTest;
    Long idUser;
    int score;

     Date dateOfSubmission;


    public AppTest() {

    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public int getScore() {
        return score;
    }

    public Long getIdTest() {
        return idTest;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }
}
