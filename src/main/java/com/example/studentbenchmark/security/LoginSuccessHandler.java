package com.example.studentbenchmark.security;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepo userRepo;

    public LoginSuccessHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AppUserEntityDetails user = (AppUserEntityDetails) authentication.getPrincipal();
        AppUser currentUser = userRepo.findByEmail(user.getEmail());
        userRepo.changeLoginDate(currentUser.getIdUser(), new Date());
    }
}
