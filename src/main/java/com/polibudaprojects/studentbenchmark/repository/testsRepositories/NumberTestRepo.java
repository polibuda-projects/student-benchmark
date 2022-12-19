package com.polibudaprojects.studentbenchmark.repository.testsRepositories;

import com.polibudaprojects.studentbenchmark.entity.testsEntities.NumberTest;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface NumberTestRepo extends TestRepo<NumberTest> {
}

