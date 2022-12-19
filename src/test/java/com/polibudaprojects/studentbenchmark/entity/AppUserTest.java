package com.polibudaprojects.studentbenchmark.entity;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.polibudaprojects.studentbenchmark.entity.AppUser.Role.ADMIN;
import static com.polibudaprojects.studentbenchmark.entity.AppUser.Role.USER;
import static org.junit.jupiter.api.Assertions.*;


class AppUserTest {
    AppUser appUser = new AppUser("F(D)i(U)l(P)i(A)p", "jasna.dupa@gmail.com", "jasnadupa", USER);
    AppUser appUserAdmin = new AppUser("F(L)i(U)l(J)i(A)p", "polska.gurom@gmail.com", "pudzian", ADMIN);

    @Test
    public void shouldCreateUserAccountCorrectly() {
        String nicknameTest = "F(D)i(U)l(P)i(A)p";
        String emailTest = "jasna.dupa@gmail.com";
        String passwordTest = "jasnadupa";

        assertAll("Should return data User",
                () -> assertEquals(nicknameTest, appUser.getNickname()),
                () -> assertEquals(emailTest, appUser.getEmail()),
                () -> assertEquals(passwordTest, appUser.getPassword()),
                () -> assertEquals(USER, appUser.getRole()));
    }

    @Test
    public void shouldCreateAdminAccountCorrectly() {
        String nicknameTest = "F(L)i(U)l(J)i(A)p";
        String emailTest = "polska.gurom@gmail.com";
        String passwordTest = "pudzian";

        assertAll("Should return data Admin",
                () -> assertEquals(nicknameTest, appUserAdmin.getNickname()),
                () -> assertEquals(emailTest, appUserAdmin.getEmail()),
                () -> assertEquals(passwordTest, appUserAdmin.getPassword()),
                () -> assertEquals(ADMIN, appUserAdmin.getRole()));
    }

    @Test
    public void shouldCreateUserAccountCorrectlyBadPath() {
        String nicknameTest = "";
        String emailTest = "";
        String passwordTest = "";

        assertAll("Should return data User",
                () -> assertNotEquals(nicknameTest, appUser.getNickname()),
                () -> assertNotEquals(emailTest, appUser.getEmail()),
                () -> assertNotEquals(passwordTest, appUser.getPassword()),
                () -> assertNotEquals(ADMIN, appUser.getRole()));
    }

    @Test
    public void shouldCreateAdminAccountCorrectlyBadPath() {
        String nicknameTest = "";
        String emailTest = "";
        String passwordTest = "";

        assertAll("Should return data Admin",
                () -> assertNotEquals(nicknameTest, appUserAdmin.getNickname()),
                () -> assertNotEquals(emailTest, appUserAdmin.getEmail()),
                () -> assertNotEquals(passwordTest, appUserAdmin.getPassword()),
                () -> assertNotEquals(USER, appUserAdmin.getRole()));
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
    public void shouldEmailBeLongerThan3Sings() {
        assertAll("Email should be longer than 3 sings",
                () -> assertNotEquals(1, appUser.getEmail().length()),
                () -> assertNotEquals(2, appUser.getEmail().length())
        );

    }

    @Test
    public void shouldNicknamedBeShorterThan65Sings() {
        int lengthNicknameTest = appUserAdmin.getNickname().length();
        Assertions.assertThat(lengthNicknameTest).isLessThan(65);
    }

    @Test
    public void shouldEmailBeShorterThan65Sings() {
        int lengthEmailTest = appUserAdmin.getEmail().length();
        Assertions.assertThat(lengthEmailTest).isLessThan(65);
    }

    @Test
    public void shouldPasswordBeShorterThan65Sings() {
        int lengthPasswordTest = appUserAdmin.getPassword().length();
        Assertions.assertThat(lengthPasswordTest).isLessThan(65);
    }
}
