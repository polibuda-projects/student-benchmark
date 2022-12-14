package com.example.studentbenchmark.repository;

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

    @Query(nativeQuery = true, value = "SELECT * FROM Logger_Entity u WHERE u.date = :dateOfLog")
    LoggerEntity findLogByDate(@Param("dateOfLog") Date dateOfLog);

    @Query(nativeQuery = true, value = "SELECT * FROM Logger_Entity u WHERE u.id_log= :idLog")
    LoggerEntity findLogsById(@Param("idLog") Long idLog);

    @Query(nativeQuery = true, value = "SELECT * FROM Logger_Entity u WHERE u.user= :userLog")
    void findLogsByUser(@Param("userLog") String userLog);

    @Query(nativeQuery = true, value = "SELECT * FROM Logger_Entity u WHERE u.context = :logContext;")
    void findLogsByMessage(@Param("logContext") String logContext);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM Logger_Entity u WHERE u.id_log = :idLog;")
    void deleteLog(@Param("idLog") Long idLog);
}

