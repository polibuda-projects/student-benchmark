//TO BE DELETED
//
//package com.example.studentbenchmark.controllers;
//
//
//import com.example.studentbenchmark.entity.AppUser;
//import com.example.studentbenchmark.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@Service
//public class UserController {
//
//    private UserRepo userRepo;
//    @Autowired
//    public UserController( UserRepo userRepo) {
//        this.userRepo=userRepo;
//    }
//    //jakaś przykładowa funkcja do wywalenia
//    @PostMapping("/user")
//    Optional<AppUser> addUser()
//    {
//
//        AppUser user= new AppUser("nickname", "email", "password", AppUser.Role.USER);
//
//        userRepo.save(user);
//
//    return Optional.ofNullable(userRepo.findUser("email", "password"));
//    }
//}