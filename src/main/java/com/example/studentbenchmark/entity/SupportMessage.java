package com.example.studentbenchmark.entity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Support;
    Long id_user;

    @NotNull
    @Size(max=64)
    String messageTitle;
    @NotBlank
    @NotNull
    @Size(min=5, max=256)
    String message;
    @Email
    @NotBlank
    @NotNull
    @Size(min=3, max=64)
    String email;

    public SupportMessage(String message,String messageTitle,String email, Long id_user){
        this.message = message;
        this.messageTitle = messageTitle;
        this.email = email;
        this.id_user = id_user;
    }

    public SupportMessage() {

    }
}

