package com.polibudaprojects.studentbenchmark.controllers;


import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import com.polibudaprojects.studentbenchmark.entity.LoggerEntity;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@RestController
@Service
public class DeleteAccountController {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final LogsRepo logsRepo;

    Logger logger = LoggerFactory.getLogger(DeleteAccountController.class);

    @Autowired
    public DeleteAccountController(UserRepo userRepo, PasswordEncoder passwordEncoder, LogsRepo logsRepo) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.logsRepo = logsRepo;
    }

    //Metoda usuwa dane użytkownika z bazy danych i go wylogowywuje
    @PostMapping("/deleteAccount")
    public ResponseEntity<String> registerUser(@Valid @RequestBody DeleteAccountRequest request, HttpServletRequest http) throws ServletException {

        //Pobiera informacje z sesji (email)
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((AppUserEntityDetails) principal).getEmail();

        //Wyszukanie użytkownika w bazie i pobranie jego hasła
        AppUser user = userRepo.findByEmail(email);
        String password = user.getPassword();

        //Sprawdznenie zgodności wpisanego hasła z obecnym hasłem użytkownika
        if (!passwordEncoder.matches(request.password(), password)) {
            logger.error("User has entered incorrect password");
            return new ResponseEntity<>("Incorrect user password", HttpStatus.BAD_REQUEST);
        }

        //Akcja usunięcia rekordów użytkownika z bazy danych
        userRepo.deleteAccount(email);

        //Zapisanie logów o pomyślnym usunięciu użytkownika z danych
        logsRepo.save(new LoggerEntity(((AppUserEntityDetails) principal).getUsername(), ((AppUserEntityDetails) principal).getId(), "User has deleted the account"));
        logger.info("User has deleted the account");

        //Wylogowanie użytkownika
        http.logout();

        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }

    //Metoda reprezentująca dane z formularza rejestracji z walidacją
    private record DeleteAccountRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String password) {
    }
}