package com.example.oauth_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//To help oauth2 server to issue, manage JWT based access tokens
//validated users credentials and handle their authentication and authorizations
@Configuration
@EnableAuthorizationServer

//defining the behaviour of the auth server while the authentication flow
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    //injecting dependencies of the user details that we defined in the prev. file
    @Autowired
    private UserDetailsService userDetailsService;

    //class functions as authentication server, authenticates the user
    @Autowired
    private AuthenticationManager authenticationManager;

    //configures client that are allowed to access the oauth server
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "refresh_token")
                .scopes("read", "write")
                .accessTokenValiditySeconds(90000)
                .refreshTokenValiditySeconds(100000)
                .redirectUris("http://localhost:8080/dummy-bookms/books")
        ;
    }

    //authorization endpoint for redirecting user to log in
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(jwtTokenStore())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    //provides various services like issuance, validation, expiration, etc of tokens
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());

        return defaultTokenServices;
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //converting token to and from JWT format
    //signing key is used by resource server (application) to authenticate the token
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("123456789012345678901234567890AB");

        return jwtAccessTokenConverter;
    }

    //to verify and encode passwords, here we are stoting it in plain text
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

