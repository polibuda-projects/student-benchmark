package com.example.studentbenchmark.repository.testsRepositories;

import com.example.studentbenchmark.entity.testsEntities.AppTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface TestRepo<T extends AppTest> extends JpaRepository<T, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName}  u ORDER BY u.score DESC LIMIT 10")
    List<T> findBestScores();
}




