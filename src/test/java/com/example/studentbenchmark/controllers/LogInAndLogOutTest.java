package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.studentbenchmark.TestConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LogInAndLogOutTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void addTestUser() {
        userRepo.save(new AppUser(USER_NICKNAME, USER_EMAIL, passwordEncoder.encode(USER_PASSWORD), AppUser.Role.USER));
    }

    @AfterEach
    public void deleteTestUser() {
        userRepo.delete(userRepo.findByEmail(USER_EMAIL));
    }

    @Test
    public void shouldReturnOk_whenSuccessfulWithNickname() throws Exception {
        mvc
                .perform(formLogin("/login").user(USER_NICKNAME).password(USER_PASSWORD))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOk_whenSuccessfulWithEmail() throws Exception {
        mvc
                .perform(formLogin("/login").user(USER_EMAIL).password(USER_PASSWORD))
                .andDo(print()).andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnRedirection_whenPasswordIsIncorrectWithNickname(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME).password(invalidPassword))
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void redirectedUrlShouldBeCorrect_whenPasswordIsIncorrectWithNickname(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME).password(invalidPassword))
                .andDo(print()).andExpect(redirectedUrl("/login?error"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnRedirection_whenPasswordIsIncorrectWithEmail(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL).password(invalidPassword))
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void redirectedUrlShouldBeCorrect_whenPasswordIsIncorrectWithEmail(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL).password(invalidPassword))
                .andDo(print()).andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void shouldReturnRedirection_whenNicknameIsIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME + "Betoniarz").password(USER_PASSWORD))
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    public void redirectedUrlShouldBeCorrect_whenNicknameIsIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME + "Betoniarz").password(USER_PASSWORD))
                .andDo(print()).andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void shouldReturnRedirection_whenEmailIsIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL + "BBBBackend").password(USER_PASSWORD))
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    public void redirectedUrlShouldBeCorrect_whenEmailIsIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL + "BBBBackend").password(USER_PASSWORD))
                .andDo(print()).andExpect(redirectedUrl("/login?error"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnRedirection_whenNicknameAndPasswordIsIncorrect(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME + "Betoniarz").password(invalidPassword))
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void redirectedUrlShouldBeCorrect_whenNicknameAndPasswordIsIncorrect(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME + "Betoniarz").password(invalidPassword))
                .andDo(print()).andExpect(redirectedUrl("/login?error"));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnRedirection_whenEmailAndPasswordIsIncorrect(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL + "BBBBackend").password(invalidPassword))
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void redirectedUrlShouldBeCorrect_whenEmailAndPasswordIsIncorrect(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL + "BBBBackend").password(invalidPassword))
                .andDo(print()).andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void shouldReturnRedirection_whenLogoutIsSuccessful() throws Exception {
        mvc
                .perform(logout())
                .andDo(print()).andExpect(status().is3xxRedirection());
    }

    @Test
    public void redirectedUrlShouldBeCorrect_whenLogoutIsSuccessful() throws Exception {
        mvc
                .perform(logout())
                .andDo(print()).andExpect(redirectedUrl("/"));
    }
}


