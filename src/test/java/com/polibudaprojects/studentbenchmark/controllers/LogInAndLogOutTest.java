package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
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

import static com.polibudaprojects.studentbenchmark.TestConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
    public void shouldReturnUnauthorized_whenPasswordIsIncorrectWithNickname() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME).password(USER_PASSWORD + "Betoniarz"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenPasswordIsIncorrectWithEmail() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL).password(USER_PASSWORD + "Betoniarz"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenNicknameIsIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME + "Betoniarz").password(USER_PASSWORD))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenEmailIsIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL + "Betoniarz").password(USER_PASSWORD))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenNicknameAndPasswordAreIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME + "Betoniarz").password(USER_PASSWORD + "Betoniarz"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenEmailAndPasswordAreIncorrect() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL + "Betoniarz").password(USER_PASSWORD + "Betoniarz"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnUnauthorized_whenPasswordIsInvalidWithNickname(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME).password(invalidPassword))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnUnauthorized_whenPasswordIsInvalidWithEmail(String invalidPassword) throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL).password(invalidPassword))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidEmails.csv")
    public void shouldReturnUnauthorized_whenEmailIsInvalid(String invalidEmails) throws Exception {

        mvc
                .perform(formLogin("/login").user(invalidEmails).password(USER_PASSWORD))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenEmailOrNicknameFieldIsEmpty() throws Exception {

        mvc
                .perform(formLogin("/login").user("").password(USER_PASSWORD))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenPasswordFieldIsEmptyWithNickname() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_NICKNAME).password(""))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenPasswordFieldIsEmptyWithEmail() throws Exception {

        mvc
                .perform(formLogin("/login").user(USER_EMAIL).password(""))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnOk_whenLogoutIsSuccessful() throws Exception {
        mvc
                .perform(logout())
                .andDo(print()).andExpect(status().isOk());
    }
}
