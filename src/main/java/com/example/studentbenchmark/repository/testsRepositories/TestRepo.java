package com.example.studentbenchmark.repository.testsRepositories;

import com.example.studentbenchmark.entity.testsEntities.AppTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface TestRepo<T extends AppTest> extends JpaRepository<T, Long> {

    //PRZYKŁADOWA FUNKCJA, POBIERA 100 NAJLEPSZYCH WYNIKÓW Z DANEJ TABELI
    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName}  u ORDER BY u.score DESC LIMIT 100")
    List<T> findBestScores();

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName}  u ORDER BY u.score ASC")
    List<T> getAllScores();

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.id_user == :id_User u ORDER BY u.score DESC LIMIT 1")
    T findPersonalBest(@Param("id_User") Long idUser);

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.score <= " + T.MAX_VALID_SCORE_GRAPH)
    List<T> findAllGraphValidScores();
}





