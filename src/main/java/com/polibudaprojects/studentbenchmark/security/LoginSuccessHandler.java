package com.polibudaprojects.studentbenchmark.security;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepo userRepo;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    public LoginSuccessHandler(UserRepo userRepo, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.logsRepo = logsRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        AppUserEntityDetails user = (AppUserEntityDetails) authentication.getPrincipal();
        AppUser currentUser = userRepo.findByEmail(user.getEmail());
        userRepo.changeLoginDate(currentUser.getIdUser(), new Date());
        logsRepo.save(new LoggerEntity(currentUser.getNickname(), currentUser.getIdUser(), "User has successfully logged in"));
        logger.info("User has successfully logged in");
    }
}
