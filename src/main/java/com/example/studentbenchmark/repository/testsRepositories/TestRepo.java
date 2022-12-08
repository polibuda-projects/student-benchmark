package com.example.studentbenchmark.repository.testsRepositories;

import com.example.studentbenchmark.entity.testsEntities.AppTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface TestRepo<T extends AppTest> extends JpaRepository<T, Long> {

    //PRZYKŁADOWA FUNKCJA, POBIERA 10 NAJLEPSZYCH WYNIKÓW Z DANEJ TABELI
    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName}  u ORDER BY u.score DESC LIMIT 10")
    List<T> findBestScores();

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.score <= " + T.MAX_VALID_SCORE_GRAPH)
    List<T> findAllGraphValidScores();
}




