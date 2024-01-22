package com.limonnana.be01.controller;


import com.limonnana.be01.entity.User;
import com.limonnana.be01.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/rest/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }



    @PostMapping("/token")
    public String token(Authentication authentication) {
        LOG.debug("Token requested for user: '{}'"+ authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: {}"+ token);
        return token;
    }

    @RequestMapping(value = "/chau",method = RequestMethod.POST)
    public String chau(){
        return "che chau !";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody User user){
        user.setUsername("rosenzvaig@gmail.com");
        user.setPassword("avocado1");
        Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String name = authentication.getName();
        String token = tokenService.generateToken(authentication);
       boolean b = authentication.isAuthenticated();
        LOG.info("authenticated: " + b);
        LOG.info("TOKEN: " + token);
        LOG.info(" 2 token: " + authentication.getPrincipal().toString());

       return name;
    }

    @GetMapping(value = "/hola")
    public String hola(){
        return "Hola todo bien ! ";
    }


    }


