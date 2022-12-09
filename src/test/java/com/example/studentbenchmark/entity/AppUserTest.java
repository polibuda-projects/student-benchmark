package com.example.studentbenchmark.entity;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class AppUserTest {
    AppUser appUser = new AppUser("F(D)i(U)l(P)i(A)p", "jasna.dupa@gmail.com", "jasnadupa", 0);
    AppUser appUser1 = new AppUser("F(L)i(U)l(J)i(A)p", "polska.gurom@gmail.com", "pudzian", 1);

    @Test
    public void shouldCreateUserAccountCorrectly() {

        String nicknameTest = "F(D)i(U)l(P)i(A)p";
        String emailTest = "jasna.dupa@gmail.com";
        String passwordTest = "jasnadupa";
        int roleTestUser = 0;

        assertAll("Should return data User",
                () -> assertEquals(nicknameTest, appUser.getNickname()),
                () -> assertEquals(emailTest, appUser.getEmail()),
                () -> assertEquals(passwordTest, appUser.getPassword()),
                () -> assertEquals(roleTestUser, appUser.getRole()));
    }

    @Test
    public void shouldCreateAdminAccountCorrectly() {
        int roleTestAdmin = 1;
        String nicknameTest = "F(L)i(U)l(J)i(A)p";
        String emailTest = "polska.gurom@gmail.com";
        String passwordTest = "pudzian";

        assertAll("Should return data Admin",
                () -> assertEquals(nicknameTest, appUser1.getNickname()),
                () -> assertEquals(emailTest, appUser1.getEmail()),
                () -> assertEquals(passwordTest, appUser1.getPassword()),
                () -> assertEquals(roleTestAdmin, appUser1.getRole()));
    }


    @Test
    public void shouldCreateUserAccountCorrectlyBadPath() {

        String nicknameTest = "";
        String emailTest = "";
        String passwordTest = "";
        int roleTestUser = 0;

        assertAll("Should return data User",
                () -> assertNotEquals(nicknameTest, appUser.getNickname()),
                () -> assertNotEquals(emailTest, appUser.getEmail()),
                () -> assertNotEquals(passwordTest, appUser.getPassword()),
                () -> assertNotEquals(roleTestUser, appUser.getRole()));
    }

    @Test
    public void shouldCreateAdminAccountCorrectlyBadPath() {
        int roleTestAdmin = 1;
        String nicknameTest = "";
        String emailTest = "";
        String passwordTest = "";

        assertAll("Should return data Admin",
                () -> assertNotEquals(nicknameTest, appUser1.getNickname()),
                () -> assertNotEquals(emailTest, appUser1.getEmail()),
                () -> assertNotEquals(passwordTest, appUser1.getPassword()),
                () -> assertNotEquals(roleTestAdmin, appUser1.getRole()));
    }






    @Test
    public void shouldNotCreateUserAboutLackOfNickname() {
        String nicknameTest = "";

        assertNotEquals(nicknameTest, appUser.getNickname());
    }


    @Test
    public void shouldNicknamedBeLongerThan3Sings() {

        assertAll("Nickname should be longer than 3 sings",
                () -> assertNotEquals(1, appUser.getNickname().length()),
                () -> assertNotEquals(2, appUser.getNickname().length())
        );

    }


    @Test
    public void shouldNotCreateUserAboutLackOfPassword() {
        String passwordTest = "";

        assertNotEquals(passwordTest, appUser.getNickname());
    }

    @Test
    public void shouldPasswordBeLongerThan3Sings() {

        assertAll("Password should be longer than 3 sings",
                () -> assertNotEquals(1, appUser.getPassword().length()),
                () -> assertNotEquals(2, appUser.getPassword().length())
        );

    }

    @Test
    public void shouldNotCreateUserAboutLackOfEmail() {
        String emailTest = "";

        assertNotEquals(emailTest, appUser.getEmail());
    }


    @Test
    public void shouldEmialBeLongerThan3Sings() {

        assertAll("Email should be longer than 3 sings",
                () -> assertNotEquals(1, appUser.getEmail().length()),
                () -> assertNotEquals(2, appUser.getEmail().length())
        );

    }


    @Test
    public void shouldNicknamedBeShorterThan65Sings() {
        int lenghtNickanmeTest=appUser1.getNickname().length();
        Assertions.assertThat(lenghtNickanmeTest).isLessThan(65);
    }

    @Test
    public void shouldEmailBeShorterThan65Sings() {
        int lenghtEmialTest=appUser1.getEmail().length();
        Assertions.assertThat(lenghtEmialTest).isLessThan(65);
    }

    @Test
    public void shouldPasswordBeShorterThan65Sings() {
        int lenghtPasswordTest=appUser1.getPassword().length();
        Assertions.assertThat(lenghtPasswordTest).isLessThan(65);
    }





}