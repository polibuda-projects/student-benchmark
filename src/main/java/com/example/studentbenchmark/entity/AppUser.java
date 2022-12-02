package com.example.studentbenchmark.entity;

import org.springframework.context.annotation.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    String nickname;
    String email;
    String password;

    int role; //role = 0  ROLE_USER  role = 1 ROLE_ADMIN
    private Date creationDate;
    private Date lastLoginDate;

    public AppUser() {
    }

    public AppUser(Long idUser, String nickname, String email, String password, int role) {
        this.idUser = idUser;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.creationDate = new Date();
        this.lastLoginDate = new Date();
        this.role = 0;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
