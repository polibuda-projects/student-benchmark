package com.example.studentbenchmark.controllers;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import com.sun.jdi.event.ExceptionEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static com.example.studentbenchmark.TestConstants.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SupportControllerTest {

    private final static String PATH = "/support";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static String messageUnderTest="It doesn't  work correctly ,why?";
    private static String TitleUnderTest="Title Under Test";
    private static String messageUnderTest2="It doesn't  work correctly ," +
            "why" +
            "?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static String TitleUnderTest2="Title Under Test aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private JsonObject SupportRequest(String title,String message){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("messageTitle",title);
        jsonObject.addProperty("message",message);
        return jsonObject;
    }
    private static RequestPostProcessor basicAuth() {
        return httpBasic(USER_NICKNAME, USER_PASSWORD);
    }

    @BeforeEach
    public void addTestUser() {
        userRepo.save(new AppUser(USER_NICKNAME, USER_EMAIL, passwordEncoder.encode(USER_PASSWORD), AppUser.Role.USER));}


    @AfterEach
    void clear(){
        userRepo.deleteAll();
    }

    @Test
    public void shouldReturnOk_whenSuccessfuly () throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SupportRequest(TitleUnderTest,messageUnderTest).toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Support message send succesfully")));
    }

    @Test
    public void shouldReturnBad_whenMessageAndTitleAreBlank () throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SupportRequest("","").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRedurnBad_whenMessageIsToLong () throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SupportRequest(TitleUnderTest2,messageUnderTest2).toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBad_whenLackOfMessage() throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SupportRequest(TitleUnderTest,"").toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBad_whenLackOfTitle() throws Exception {
        mvc.
                perform(post(PATH).with(basicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SupportRequest("",messageUnderTest).toString()))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBad_whenUserIsAnuthorised() throws Exception {
        mvc.
                perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SupportRequest(TitleUnderTest,messageUnderTest).toString()))
                .andDo(print()).andExpect(status().isUnauthorized());
    }




}





