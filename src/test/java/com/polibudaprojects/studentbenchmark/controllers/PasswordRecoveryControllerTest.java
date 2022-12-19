package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
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
public class PasswordRecoveryControllerTest {

    private final static String PATH1 = "/passwordRecovery";
    private final static String PATH2 = "/resetPassword";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private JsonObject createPasswordRecoveryRequest(String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        return jsonObject;
    }

    private JsonObject createResetPasswordRequest(String token, String newPassword, String newPasswordRepeated) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("newPassword", newPassword);
        jsonObject.addProperty("newPasswordRepeated", newPasswordRepeated);
        return jsonObject;
    }

    private JsonObject createInvalidResetPasswordRequest(String newPassword, String newPasswordRepeated) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("newPassword", newPassword);
        jsonObject.addProperty("newPasswordRepeated", newPasswordRepeated);
        return jsonObject;
    }

    @Test
    public void shouldReturnBadRequest_emailNotIn() throws Exception {
        userRepo.save(new AppUser("nickname", "aaaa@aal.com", passwordEncoder.encode("aA@asdaki1"), AppUser.Role.USER));
        mvc
                .perform(post(PATH1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPasswordRecoveryRequest("").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid email address")));
        if (userRepo.findByEmail("aaaa@aal.com") != null) {
            userRepo.delete(userRepo.findByEmail("aaaa@aal.com"));
        }
    }

    @Test
    public void shouldReturnBadRequest_noToken() throws Exception {
        mvc
                .perform(post(PATH2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createInvalidResetPasswordRequest("aA@xdcf4", "aA@xdcf4").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Token not found")));
    }

    @Test
    public void shouldReturnBadRequest_invalidToken() throws Exception {
        mvc
                .perform(post(PATH2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createResetPasswordRequest("A5", "aA@xdcf4", "aA@xdcf4").toString()))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Token not found")));
    }
}
