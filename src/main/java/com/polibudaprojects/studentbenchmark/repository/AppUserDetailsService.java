package com.polibudaprojects.studentbenchmark.repository;

import com.polibudaprojects.studentbenchmark.entity.AppUser;
import com.polibudaprojects.studentbenchmark.entity.AppUserEntityDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public AppUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserDetails user = userRepo.findByNickname(usernameOrEmail).map(AppUserEntityDetails::new).orElse(null);
        if (user == null) {
            AppUser userByEmail = userRepo.findByEmail(usernameOrEmail);
            if (userByEmail == null) {
                throw new UsernameNotFoundException(usernameOrEmail);
            }
            user =  new AppUserEntityDetails(userByEmail);
        }

        return user;
    }
}
