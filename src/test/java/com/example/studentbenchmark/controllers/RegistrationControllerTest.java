package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static com.example.studentbenchmark.TestConstants.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    private final static String PATH = "/register";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private JsonObject createRegisterRequest(String nickname, String email, String password, String passwordConfirmation) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nickname", nickname);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("passwordConfirmation", passwordConfirmation);
        return jsonObject;
    }


    @Test
    public void shouldReturnOk_whenSuccessful() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("User registered successfully")));
        userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
    }

    @Test
    public void shouldReturnBadRequest_whenPasswordConfirmationIsWrong() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "aA@asapisi2").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Passwords do not match")));
    }

    @Test
    public void shouldReturnBadRequest_whenPasswordConfirmationIsEmpty() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequest_whenNicknameIsEmpty() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("", "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequest_whenEmailIsEmpty() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "", "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequest_whenPasswordIsEmpty() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequest_whenUserWithThisEmailAlreadyExists() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()));

        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick5", "nick2@email.pl", "aA@asapiski3", "aA@asapiski3").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("This email is used by existing account")));

        userRepo.delete(userRepo.findByEmail("nick2@email.pl"));

    }

    @Test
    public void shouldReturnBadRequest_whenUserWithThisNicknameAlreadyExists() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()));

        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick5@email.pl", "aA@asapiski3", "aA@asapiski3").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("This nickname is used by existing account")));

        userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aA@asapiski", "aAasapiski3", "a@asapiski3", "AA@ASAPISKI3", "aA@3p", "aA@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaas"})
    public void shouldReturnBadRequest_whenPasswordIsInvalid(String password) throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", password, password).toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("<br/>" + "   Password must contain at least one digit [0-9]." +
                        "<br/>" + "   Password must contain at least one lowercase Latin character [a-z]." +
                        "<br/>" + "    Password must contain at least one uppercase Latin character [A-Z]." +
                        "<br/>" + "    Password must contain at least one special character like ! @ # & ( )." +
                        "<br/>" + "    Password must contain a length of at least 8 characters and a maximum of 64 characters.")));

    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aA@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaaaaaaaasssssssssssaaaaaaaaaaaaas", "aaa@", "username.@domain.com", ".user.name@domain.com", "username@.com", "user-name@domain.com."})
    public void shouldReturnBadRequest_whenEmailIsInvalid(String email) throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", email, "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa", "aA@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaaaaaaaasssssssssssaaaaaaaaaaaaas"})
    public void shouldReturnBadRequest_whenNicknameIsInvalid(String nick) throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest(nick, "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("size must be between 3 and 64")));
    }

}


