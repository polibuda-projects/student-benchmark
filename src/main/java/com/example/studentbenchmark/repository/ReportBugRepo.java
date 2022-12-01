package com.example.studentbenchmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface ReportBugRepo extends JpaRepository{

    @Modifying
    @Transactional
    @Query(value = "insert into ReportBug (user_id, message, date) VALUES (:userID,:message,:date)", nativeQuery = true)
    void newReportBug(@Param("userID") Long idUser, @Param("message") String message, @Param("date") Date dateOfSubmission);

}
