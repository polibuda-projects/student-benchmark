package com.example.studentbenchmark.security;

import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.LoggerEntity;
import com.example.studentbenchmark.repository.LogsRepo;
import com.example.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepo userRepo;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    public LoginSuccessHandler(UserRepo userRepo, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.logsRepo = logsRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AppUserEntityDetails user = (AppUserEntityDetails) authentication.getPrincipal();
        AppUser currentUser = userRepo.findByEmail(user.getEmail());
        userRepo.changeLoginDate(currentUser.getIdUser(), new Date());
        logsRepo.save(new LoggerEntity(currentUser.getNickname(), currentUser.getIdUser(), sqlDate, "User has successfully logged in"));
        logger.info("User has successfully logged in");
    }
}
