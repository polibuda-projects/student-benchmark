package com.example.studentbenchmark.repository;

import com.example.studentbenchmark.entity.AppUserEntityDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public AppUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByNickname(username).map(AppUserEntityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
