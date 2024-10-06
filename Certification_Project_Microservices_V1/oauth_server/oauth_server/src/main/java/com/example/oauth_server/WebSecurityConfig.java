package com.example.oauth_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//this file customizes the security features of Oauth server MS
//basic user authentication mechanism
@Configuration
//Enables spring security web features
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //here we are creating in memory user but we can fetch user from jpa DB
    @Bean
    public UserDetailsService uds() {
        UserDetails prachi = User.withUsername("prachi")
                .password("123")
                .authorities("read", "write")
                .build();

        //storing prachi info in, in memory
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(prachi);
        return userDetailsManager;
    }

    @Bean
    //AuthenticationManager is feature provided by spring security
    //authenticates users, communicates with auth server
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
    }

}
