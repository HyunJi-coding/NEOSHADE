package org.example.login.config.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HandlerLoginSucess handlerLoginSucess;

    @Autowired
    HandlerLoginFailure handlerLoginFailure;

    @Autowired
    HandlerLogoutSucess handlerLogoutSucess;

    @Autowired
    HandlerAccessDeny handlerAccessDeny;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/home","/comm/**","/img/**","/css/**","/js/**","/docs/**","/secure/**","/users/**", "/products/**", "/api/**").permitAll()
                .antMatchers("/setting/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/users/login")
                .loginProcessingUrl("/users/login_exe")
                .successHandler(handlerLoginSucess)
                .failureHandler(handlerLoginFailure)
                .permitAll();

        http.logout()
                .logoutUrl("/users/logout")
                .logoutSuccessHandler(handlerLogoutSucess)
                .permitAll();

        http.exceptionHandling()
                .accessDeniedHandler(handlerAccessDeny);

        http.csrf()
                .disable();
    }
}