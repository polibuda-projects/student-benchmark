package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.example.studentbenchmark.TestConstants.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



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
    @Autowired


    private JsonObject delateAccountRequest (String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);
        return jsonObject;
    }

    private static RequestPostProcessor basicAuth() {
        return httpBasic(USER_NICKNAME, USER_PASSWORD);
    }

    @BeforeEach
    public void addTestUserAndSEtUp() {
        userRepo.save(new AppUser(USER_NICKNAME, USER_EMAIL, passwordEncoder.encode(USER_PASSWORD),
                AppUser.Role.USER));
    }





        @Test
        //@WithMockUser
        public void shouldReturnOk_whenSuccessfuly () throws Exception {
            mvc
                    .perform(post(PATH).with(user(new AppUserEntityDetails(userRepo.findByEmail(USER_EMAIL))))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(delateAccountRequest( "P@ssw0rd").toString()))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("User Deleted")));

        }





    @Test
    public void shouldReturnBadRequest_whenNotSuccessfuly () throws Exception {
        mvc
                .perform(post(PATH).with(user(new AppUserEntityDetails(userRepo.findByEmail(USER_EMAIL))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("P@ssw0rd123").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Incorrect user password")));

    }


    @Test
    public void shouldDeleteUser () throws Exception {
        mvc
                .perform(post(PATH).with(user(new AppUserEntityDetails(userRepo.findByEmail(USER_EMAIL))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("P@ssw0rd").toString()))
                .andDo(print());
        assertTrue(userRepo.findByNickname("nickname").isEmpty());
    }

    @Test//do zastanowiena się
    public void shouldReturnUnautorised_withoutAuthorization () throws Exception {
        mvc
                .perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest("P@ssw0rd").toString()))
                .andDo(print()).andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldReturnBadRequest_whenPasswordIsEmpty() throws Exception {
        mvc
                .perform(post(PATH).with(user(new AppUserEntityDetails(userRepo.findByEmail(USER_EMAIL))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest( "").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }



    @ParameterizedTest
    @ValueSource(strings = {"aA@asapiski", "aAasapiski3", "a@asapiski3", "AA@ASAPISKI3", "aA@3p", "aA" +
            "@asapiski3sssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaasasasaaddddddddddddaaaaaaaaaaaaaaaaaas"})
    public void shouldReturnBadRequest_whenPasswordIsInvalid(String password) throws Exception {
        mvc
                .perform(post(PATH).with(user(new AppUserEntityDetails(userRepo.findByEmail(USER_EMAIL))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(delateAccountRequest(password).toString()))
                .andDo(print()).andExpect(status().isBadRequest());

    }



}


