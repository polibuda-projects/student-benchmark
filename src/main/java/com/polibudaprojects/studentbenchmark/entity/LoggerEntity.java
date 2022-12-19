package com.polibudaprojects.studentbenchmark.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LoggerEntity {

    @Transient
    private final java.util.Date utilDate = new java.util.Date();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLog;
    private String user;
    private Long userId;
    private Date date;
    private String context;

    public LoggerEntity(String user, Long userId, Date date, String context) {
        this.user = user;
        this.userId = userId;
        this.date = date;
        this.context = context;
    }

    public LoggerEntity(String user, Long userId, String context) {
        this.user = user;
        this.userId = userId;
        this.date = getCurrentDate();
        this.context = context;
    }

    public LoggerEntity() {

    }

    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private java.sql.Date getCurrentDate() {
        return new java.sql.Date(utilDate.getTime());
    }
}
