package com.polibudaprojects.studentbenchmark.controllers;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.entity.SupportMessage;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.SupportRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Date;
import java.util.List;
import static com.polibudaprojects.studentbenchmark.entity.AppUser.Role.ADMIN;
import static com.polibudaprojects.studentbenchmark.entity.AppUser.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminDashboardControllerTest {
    @Autowired
    private LogsRepo logsRepo;
    @Autowired
    private SupportRepo supportRepo;
    @Autowired
    private AdminDashboardController adminDashboardController;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        logsRepo = Mockito.mock(LogsRepo.class);
        supportRepo = Mockito.mock(SupportRepo.class);
        authentication = Mockito.mock(Authentication.class);
        adminDashboardController = new AdminDashboardController(logsRepo, supportRepo);
    }

    @Test
    public void testAdminDashboardLogs_whenUserIsAdmin_thenReturnLogs() {

        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("admin", "admin@email.com", "admin1", ADMIN));
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        Mockito.when(logsRepo.findAll()).thenReturn(List.of(new LoggerEntity("admin", 1L, new Date(), "admin")));
        currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<String> result = adminDashboardController.adminDashboardLogs();
        assertAll("Should return",
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertTrue(result.getBody().contains("admin")),
                () -> assertTrue(result.getBody().contains("1")));
    }

    @Test
    public void testAdminDashboardLogs_whenUserIsNotAdmin_thenThrowException() {

        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("user", "user@email.com", "user1", USER));
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assertThrows(RuntimeException.class, () -> adminDashboardController.adminDashboardLogs());
    }

    @Test
    public void testAdminDashboardMessages_whenUserIsAdmin_thenReturnMessages() {
        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("admin", "admin@email.com", "admin1", ADMIN));
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(supportRepo.findAll()).thenReturn(List.of(new SupportMessage("messageBody", "messageTitle", "user@mail.com", 1L)));
        ResponseEntity<String> result = adminDashboardController.adminDashboardMessages();
        assertAll("Should return",
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertTrue(result.getBody().contains("messageBody")),
                () -> assertTrue(result.getBody().contains("messageTitle")),
                () -> assertTrue(result.getBody().contains("user@mail.com")),
                () -> assertTrue(result.getBody().contains("1")));

    }

    @Test
    public void testAdminDashboardMessages_whenUserIsNotAdmin_thenThrowException() {

        AppUserEntityDetails currentUser = new AppUserEntityDetails(new AppUser("user", "user@email.com", "user1", USER));
        Mockito.when(authentication.getPrincipal()).thenReturn(currentUser);
        currentUser.getAuthorities().toString().equals("[ROLE_ADMIN]");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assertThrows(RuntimeException.class, () -> adminDashboardController.adminDashboardMessages());
    }
}