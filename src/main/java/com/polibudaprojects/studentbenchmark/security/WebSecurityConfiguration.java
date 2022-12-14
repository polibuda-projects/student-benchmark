package com.polibudaprojects.studentbenchmark.security;

import com.polibudaprojects.studentbenchmark.repository.AppUserDetailsService;
import com.polibudaprojects.studentbenchmark.repository.LogsRepo;
import com.polibudaprojects.studentbenchmark.repository.UserRepo;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserRepo userRepo;

    private final LogsRepo logsRepo;

    @Autowired
    public WebSecurityConfiguration(UserRepo userRepo, LogsRepo logsRepo) {
        this.userRepo = userRepo;
        this.logsRepo = logsRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/", "/register", "/result/*", "/tests/*", "/passwordRecovery", "/resetPassword")
                .permitAll()
                .mvcMatchers("/adminDashboard/logs", "/adminDashboard/messages").access("hasRole('ROLE_ADMIN')")
                .mvcMatchers("/changeUserPassword", "/DashboardPublic", "/DashboardPersonal", "/deleteAccount",
                        "/support", "/user")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").successHandler(new LoginSuccessHandler(userRepo, logsRepo))
                .failureHandler((request, response, exception) -> {
                    response.setContentType("text/plain");
                    response.getWriter().write("Invalid username or password");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    response.flushBuffer();
                })
                .and()
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable()
                .sessionManagement().invalidSessionStrategy((request, response) -> {
                    response.setStatus(400);
                    response.addHeader("session-expired", "true");
                })
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(1).maxSessionsPreventsLogin(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        AppUserDetailsService detailsService = new AppUserDetailsService(userRepo);
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

