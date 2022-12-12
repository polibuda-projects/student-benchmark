package com.example.studentbenchmark.entity;

import com.example.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static com.example.studentbenchmark.entity.TestConstants.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeUserPasswordControllerTest {

    private final static String PATH = "/changeUserPassword";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static RequestPostProcessor basicAuth() {
        return httpBasic(USER_NICKNAME, USER_PASSWORD);
    }

    @BeforeEach
    public void addTestUser() {
        userRepo.save(new AppUser(USER_NICKNAME, USER_EMAIL, passwordEncoder.encode(USER_PASSWORD), AppUser.Role.USER));
    }

    @AfterEach
    void clearRepo() {
        userRepo.deleteAll();
    }

    @Test
    public void shouldReturnOk_whenSuccessful() throws Exception {
        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("User password changed successfully")));
    }

    @Test
    public void shouldChangeUserPassword() throws Exception {
        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()))
                .andDo(print());

        AppUser user = userRepo.findByEmail(USER_EMAIL);
        Assertions.assertTrue(passwordEncoder.matches(USER_NEW_PASSWORD, user.getPassword()));
    }

    @Test
    public void shouldChangeUserPasswordAgain() throws Exception {
        String NEWER_PASSWORD = "P@ssw0rdNEWER";
        JsonObject nextRequest = new JsonObject();
        nextRequest.addProperty("oldPassword", USER_NEW_PASSWORD);
        nextRequest.addProperty("newPassword", NEWER_PASSWORD);
        nextRequest.addProperty("newPasswordRepeated", NEWER_PASSWORD);

        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()));

        mvc
                .perform(post(PATH).with(httpBasic(USER_NICKNAME, USER_NEW_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nextRequest.toString()))
                .andDo(print());

        AppUser user = userRepo.findByEmail(USER_EMAIL);
        Assertions.assertTrue(passwordEncoder.matches(NEWER_PASSWORD, user.getPassword()));
    }

    @Test
    public void shouldReturnBadRequest_whenOldPasswordIsIncorrect() throws Exception {
        JsonObject badRequest = CHANGE_USER_PASSWORD_REQUEST.deepCopy();
        badRequest.addProperty("oldPassword", USER_PASSWORD + "#");

        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest.toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Received incorrect user old password")));
    }

    @Test
    public void shouldReturnBadRequest_whenNewPasswordsDoNotMatch() throws Exception {
        JsonObject badRequest = CHANGE_USER_PASSWORD_REQUEST.deepCopy();
        badRequest.addProperty("newPasswordRepeated", USER_NEW_PASSWORD + "#");

        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest.toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Passwords do not match")));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidPasswords.csv")
    public void shouldReturnBadRequest_whenNewPasswordIsInvalid(String invalidPassword) throws Exception {
        JsonObject badRequest = CHANGE_USER_PASSWORD_REQUEST.deepCopy();
        badRequest.addProperty("newPassword", invalidPassword);
        badRequest.addProperty("newPasswordRepeated", invalidPassword);

        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest.toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnauthorized_withoutAuthorization() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_withAnonymousAuthorization() throws Exception {
        mvc
                .perform(post(PATH).with(anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenAuthorizationUsernameIsIncorrect() throws Exception {
        mvc
                .perform(post(PATH).with(httpBasic(USER_NICKNAME + "#", USER_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenAuthorizationPasswordIsIncorrect() throws Exception {
        mvc
                .perform(post(PATH).with(httpBasic(USER_NICKNAME, USER_PASSWORD + "#"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHANGE_USER_PASSWORD_REQUEST.toString()))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(strings = {"oldPassword", "newPassword", "newPasswordRepeated"})
    public void shouldReturnBadRequest_whenRequestPropertyIsNull(String nullKey) throws Exception {
        JsonObject incompleteRequest = CHANGE_USER_PASSWORD_REQUEST.deepCopy();
        incompleteRequest.remove(nullKey);

        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incompleteRequest.toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"oldPassword", "newPassword", "newPasswordRepeated"})
    public void shouldReturnBadRequest_whenRequestPropertyIsEmpty(String emptyKey) throws Exception {
        JsonObject incompleteRequest = CHANGE_USER_PASSWORD_REQUEST.deepCopy();
        incompleteRequest.addProperty(emptyKey, "");

        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incompleteRequest.toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
