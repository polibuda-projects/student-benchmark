package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.transaction.annotation.Transactional;

import static com.example.studentbenchmark.TestConstants.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteAccountControllerTest {

    private final static String PATH = "/deleteAccount";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private JsonObject delateAccountRequest(String nickname, String email, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nickname", nickname);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        return jsonObject;
    }

    private static RequestPostProcessor basicAuth() {
        return httpBasic(USER_NICKNAME, USER_PASSWORD);
    }

    @BeforeEach
    public void addTestUser() {
        userRepo.save(new AppUser(USER_NICKNAME, USER_EMAIL, passwordEncoder.encode(USER_PASSWORD),
                AppUser.Role.USER));}

        @Test
        public void shouldReturnOk_whenSuccessfuly () throws Exception {
            mvc.
                    perform(post(PATH).with(basicAuth())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(delateAccountRequest("nickname", "email@email.com", "P@ssw0rd").toString()))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("User Deleted")));

        }




    @Test
    public void shouldReturnBadRequest_whenNotSuccessfuly () throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("nickname", "email@email.com", "P@ssw0rd123").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Incorrect user password")));

    }


    @Test
    public void shouldDelateUser () throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("nickname", "email@email.com", "P@ssw0rd").toString()))
                .andDo(print());
        assertEquals(null,userRepo.findUser("email@email.com","P@ssw0rd"));
    }

    @Test//do zastanowiena się
    public void shouldReturnUnautorised_withoutAuthorization () throws Exception {
        mvc.
                perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("nickname", "email@email.com", "P@ssw0rd").toString()))
                .andDo(print()).andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldReturnBadRequest_whenPasswordIsEmpty() throws Exception {
        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("nickname", "email@email.com", "").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }



    @ParameterizedTest
    @ValueSource(strings = {"aA@asapiski", "aAasapiski3", "a@asapiski3", "AA@ASAPISKI3", "aA@3p", "aA" +
            "@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaas"})
    public void shouldReturnBadRequest_whenPasswordIsInvalid(String password) throws Exception {
        mvc
                .perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("nickname", "email@email.com", password).toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("<br/>" + "   Password must contain at least one digit [0-9]." +
                        "<br/>" + "   Password must contain at least one lowercase Latin character [a-z]." +
                        "<br/>" + "    Password must contain at least one uppercase Latin character [A-Z]." +
                        "<br/>" + "    Password must contain at least one special character like ! @ # & ( )." +
                        "<br/>" + "    Password must contain a length of at least 8 characters and a maximum of 64 characters.")));

    }


}


