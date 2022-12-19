package com.polibudaprojects.studentbenchmark.repository.testsRepositories;

import com.polibudaprojects.studentbenchmark.entity.testsEntities.SequenceTest;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface SequenceTestRepo extends TestRepo<SequenceTest> {
}

