package com.example.studentbenchmark.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppUserEntityDetails implements UserDetails {
    private final AppUser entity;

    public AppUserEntityDetails(AppUser entity) {
        this.entity = entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(entity.getRole() == 1)
        {
            return Stream.of("ROLE_ADMIN")
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

        return Stream.of("ROLE_USER")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @Override
    public String getUsername() {
        return entity.getEmail();
    }

    public String getEmail() {
        return entity.getEmail();
    }//dodałem bo getUsername() wprowadza w błąd

    public Long getId()
    {
        return entity.getIdUser();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
