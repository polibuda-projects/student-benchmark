package com.example.studentbenchmark.repository.testsRepositories;

import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface VerbalTestRepo extends TestRepo<VerbalTest> {
    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.score <= " + VerbalTest.MAX_VALID_SCORE_GRAPH)
    List<VerbalTest> findAllGraphValidScores();
}
