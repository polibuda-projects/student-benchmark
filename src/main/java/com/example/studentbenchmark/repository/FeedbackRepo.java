//package com.example.studentbenchmark.repository;
//
//import java.util.Date;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//@Repository
//public interface FeedbackRepo extends JpaRepository{
//
//    @Modifying
//    @Transactional
//    @Query(value = "insert into Feedback (user_id, message, rating, date) VALUES (:userID,:message,:rating,:date)", nativeQuery = true)
//    void newFeedback(@Param("userID") Long idUser, @Param("message") String message, @Param("rating") int rating, @Param("date") Date dateOfSubmission);
//
//}
