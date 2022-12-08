package com.example.studentbenchmark.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SupportMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Support;
    Long id_user;
    String message;
    String email;

    public SupportMessage(String message, String email, Long id_user){
        this.message = message;
        this.email = email;
        this.id_user = id_user;
    }
}
