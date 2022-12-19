package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
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
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
    }

    @Test
    public void shouldReturnBadRequest_whenPasswordConfirmationIsWrong() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "aA@asapisi2").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Passwords do not match")));
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
    }

    @Test
    public void shouldReturnBadRequest_whenPasswordConfirmationIsEmpty() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", "aA@asapiski2", "").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
    }

    @Test
    public void shouldReturnBadRequest_whenNicknameIsEmpty() throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("", "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
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
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
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

        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }

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

        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"aA@asapiski", "aAasapiski3", "a@asapiski3", "AA@ASAPISKI3", "aA@3p", "aA@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaas"})
    public void shouldReturnBadRequest_whenPasswordIsInvalid(String password) throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", "nick2@email.pl", password, password).toString()))
                .andDo(print()).andExpect(status().isBadRequest());
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aA@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaaaaaaaasssssssssssaaaaaaaaaaaaas", "aaa@", "username.@domain.com", ".user.name@domain.com", "username@.com", "user-name@domain.com."})
    public void shouldReturnBadRequest_whenEmailIsInvalid(String email) throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest("nick2", email, "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
        if (userRepo.findByEmail(email) != null) {
            userRepo.delete(userRepo.findByEmail(email));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa", "aA@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaaaaaaaasssssssssssaaaaaaaaaaaaas"})
    public void shouldReturnBadRequest_whenNicknameIsInvalid(String nick) throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRegisterRequest(nick, "nick2@email.pl", "aA@asapiski2", "aA@asapiski2").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
        if (userRepo.findByEmail("nick2@email.pl") != null) {
            userRepo.delete(userRepo.findByEmail("nick2@email.pl"));
        }
    }
}
