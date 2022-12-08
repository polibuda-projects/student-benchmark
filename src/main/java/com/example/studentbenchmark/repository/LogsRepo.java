package com.example.studentbenchmark.repository;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public interface LogsRepo extends JpaRepository<LoggerEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM LoggerEntity u WHERE u.dateOfLog = :dateOfLog")
    AppUser findLogByDate(@Param("dateOfLog") Date dateOfLog);

    @Query(nativeQuery = true, value = "SELECT * FROM LoggerEntity u WHERE u.idLog= :idLog")
    AppUser findLogsById(@Param("idLog") Long idLog);

    @Query(nativeQuery = true, value = "SELECT * FROM LoggerEntity u WHERE u.userLog= :userLog")
    void findLogsByUser(@Param("userLog") String userLog);

    @Query(nativeQuery = true, value = "SELECT * FROM LoggerEntity u WHERE u.logContext = :logContext;")
    void findLogsByMessage(@Param("logContext") String logContext);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM LoggerEntity u WHERE u.idLog = :idLog;")
    void deleteLog(@Param("idLog") Long idLog);
}
