package com.polibudaprojects.studentbenchmark.repository.testsRepositories;

import com.polibudaprojects.studentbenchmark.entity.testsEntities.AppTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;


@NoRepositoryBean
public interface TestRepo<T extends AppTest> extends JpaRepository<T, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u JOIN app_user ON u.id_user = app_user.id_user ORDER BY u.score DESC LIMIT 100")
    List<T> findBestScores();

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName}  u ORDER BY u.score ASC")
    List<T> getAllScores();

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.id_user = :idUser ORDER BY u.score DESC LIMIT 1")
    List<T> findPersonal(@Param("idUser") Long idUser);

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.id_user = :idUser ORDER BY u.score")
    List<T> findAllPersonal(@Param("idUser") Long idUser);

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.id_user = :idUser u ORDER BY u.score DESC LIMIT 1")
    T findPersonalBest(@Param("idUser") Long idUser);

    @Query(nativeQuery = true, value = "SELECT * FROM #{#entityName} u WHERE u.score <= " + T.MAX_VALID_SCORE_GRAPH)
    List<T> findAllGraphValidScores();
}




