package com.example.studentbenchmark.repository.testsRepositories;

import com.example.studentbenchmark.entity.testsEntities.NumberTest;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface NumberTestRepo extends TestRepo<NumberTest> {
}
