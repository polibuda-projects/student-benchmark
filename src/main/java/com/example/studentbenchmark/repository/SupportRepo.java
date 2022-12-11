package com.example.studentbenchmark.repository;

import com.example.studentbenchmark.entity.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupportRepo extends JpaRepository<SupportMessage, Long> {
}