package com.example.studentbenchmark.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @NotNull
    @NotBlank(message = "nickname is mandatory")
    @Size(min = 3, max = 64)
    private String nickname;
    @Email
    @NotBlank
    @NotNull
    @Size(min = 3, max = 64)
    private String email;
    @NotNull
    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
            "<br/>" + "   Password must contain at least one digit [0-9]." +
                    "<br/>" + "   Password must contain at least one lowercase Latin character [a-z]." +
                    "<br/>" + "    Password must contain at least one uppercase Latin character [A-Z]." +
                    "<br/>" + "    Password must contain at least one special character like ! @ # & ( )." +
                    "<br/>" + "    Password must contain a length of at least 8 characters and a maximum of 64 characters.")
    private String password;
    private Role role;
    private Date creationDate;
    private Date lastLoginDate;

    public AppUser() {
    }

    public AppUser(String nickname, String email, String password, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.creationDate = new Date();
        this.lastLoginDate = new Date();
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        USER,
        ADMIN
    }
}
