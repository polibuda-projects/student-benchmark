package com.example.studentbenchmark.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LoggerEntity {

    @Transient
    private final java.util.Date utilDate = new java.util.Date();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLog;
    private String userLog;
    private Long userLogId;
    private Date dateOfLog;
    private String logContext;

    public LoggerEntity(String userLog, Long userLogId, Date dateOfLog, String logContext) {
        this.userLog = userLog;
        this.userLogId = userLogId;
        this.dateOfLog = dateOfLog;
        this.logContext = logContext;
    }

    public LoggerEntity(String userLog, Long userLogId, String logContext) {
        this.userLog = userLog;
        this.userLogId = userLogId;
        this.dateOfLog = getCurrentDate();
        this.logContext = logContext;
    }

    public LoggerEntity() {

    }

    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public String getUserLog() {
        return userLog;
    }

    public void setUserLog(String userLog) {
        this.userLog = userLog;
    }

    public Long getUserLogId() {
        return userLogId;
    }

    public void setUserLogId(Long userLogId) {
        this.userLogId = userLogId;
    }

    public Date getDateOfLog() {
        return dateOfLog;
    }

    public void setDateOfLog(Date dateOfLog) {
        this.dateOfLog = dateOfLog;
    }

    public String getLogContext() {
        return logContext;
    }

    public void setLogContext(String logContext) {
        this.logContext = logContext;
    }

    private java.sql.Date getCurrentDate() {
        return new java.sql.Date(utilDate.getTime());
    }
}
