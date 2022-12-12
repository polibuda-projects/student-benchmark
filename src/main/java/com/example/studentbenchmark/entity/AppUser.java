package com.example.studentbenchmark.entity;


import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.*;


@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotNull
    @NotBlank(message = "nickname is mandatory")
    @Size(min=3, max=64)
    String nickname;

    @Email
    @NotBlank
    @NotNull
    @Size(min=3, max=64)
    String email;
    @NotNull
    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
            "<br/>"+"   Password must contain at least one digit [0-9]." +
            "<br/>"+"   Password must contain at least one lowercase Latin character [a-z]." +
                    "<br/>"+"    Password must contain at least one uppercase Latin character [A-Z]." +
                    "<br/>"+"    Password must contain at least one special character like ! @ # & ( )." +
                    "<br/>"+ "    Password must contain a length of at least 8 characters and a maximum of 64 characters.")
    String password;

    String passwordConfirmation;

    int role; //role = 0  ROLE_USER  role = 1 ROLE_ADMIN
    private Date creationDate;
    private Date lastLoginDate;

    public AppUser() {
    }


    public AppUser(String nickname, String email, String password, int role) {
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
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

