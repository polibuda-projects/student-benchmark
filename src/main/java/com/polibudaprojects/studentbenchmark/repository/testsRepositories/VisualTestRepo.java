package com.polibudaprojects.studentbenchmark.repository.testsRepositories;

import com.polibudaprojects.studentbenchmark.entity.testsEntities.VisualTest;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface VisualTestRepo extends TestRepo<VisualTest> {
}
