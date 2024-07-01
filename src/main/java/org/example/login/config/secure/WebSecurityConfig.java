package org.example.login.config.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    HandlerLoginSucess handlerLoginSucess;

    @Autowired
    HandlerLoginFailure handlerLoginFailure;

    @Autowired
    HandlerLogoutSucess handlerLogoutSucess;

    @Autowired
    HandlerAccessDeny handlerAccessDeny;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/", "/home", "/comm/**", "/img/**", "/css/**", "/js/**", "/docs/**", "/secure/login","/users/**").permitAll()
                        .requestMatchers("/study/**").hasAnyRole("MEMBER", "ADMIN")
                        .requestMatchers("/member/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        );

        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/secure/login")
                        .loginProcessingUrl("/secure/login_exe")
                        .successHandler(handlerLoginSucess)
                        .failureHandler(handlerLoginFailure)
                        .permitAll()
        );

        http.logout(logout ->
                logout
                        .logoutSuccessHandler(handlerLogoutSucess)
                        .permitAll()
        );

        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .accessDeniedHandler(handlerAccessDeny)
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
        return manager;
    }
}