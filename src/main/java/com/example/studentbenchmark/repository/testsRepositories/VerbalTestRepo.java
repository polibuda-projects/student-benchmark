package com.example.studentbenchmark.repository.testsRepositories;

import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface VerbalTestRepo extends TestRepo<VerbalTest> {
}
