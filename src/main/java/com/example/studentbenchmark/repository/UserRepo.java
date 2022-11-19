package com.example.studentbenchmark.repository;

import com.example.studentbenchmark.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {


    AppUser findByEmail(String email);
    @Query(nativeQuery = true, value= "SELECT * FROM App_User u WHERE u.email= :email AND u.password= :password")
    AppUser findUser(@Param("email") String email, @Param("password")String password);
}
