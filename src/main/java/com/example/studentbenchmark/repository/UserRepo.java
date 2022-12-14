package com.example.studentbenchmark.repository;

import com.example.studentbenchmark.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByNickname(String username);

    AppUser findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM app_user WHERE email= :email AND password= :password")
    AppUser findUser(@Param("email") String email, @Param("password") String password);

    @Query(nativeQuery = true, value = "SELECT * FROM app_user WHERE id_user= :idUser")
    AppUser findByID(@Param("idUser") Long idUser);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE app_user SET password= :newPassword WHERE id_user= :idUser")
    void changeUserPassword(@Param("idUser") Long idUser, @Param("newPassword") String newPassword);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM app_user WHERE  email= :email")
    void deleteAccount(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE app_user SET last_login_date= :lastLoginDate WHERE id_user= :idUser")
    void changeLoginDate(@Param("idUser") Long idUser, @Param("lastLoginDate") Date lastLoginDate);
}
