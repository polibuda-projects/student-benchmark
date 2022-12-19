package com.polibudaprojects.studentbenchmark.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SupportMessage {
    Long id_user;
    @NotNull
    @Size(max = 64)
    String messageTitle;
    @NotBlank
    @NotNull
    @Size(min = 5, max = 256)
    String message;
    @Email
    @NotBlank
    @NotNull
    @Size(min = 3, max = 64)
    String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Support;

    public SupportMessage(String message, String messageTitle, String email, Long id_user) {
        this.message = message;
        this.messageTitle = messageTitle;
        this.email = email;
        this.id_user = id_user;
    }

    public SupportMessage() {

    }

    public Long getId_Support() {
        return id_Support;
    }

    public void setId_Support(Long id_Support) {
        this.id_Support = id_Support;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
