package com.limonnana.be01.controller;


import com.limonnana.be01.entity.User;
import com.limonnana.be01.service.TokenService;
import com.limonnana.be01.service.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/rest/auth")
public class AuthController {


    private PasswordEncoder passwordEncoder;

    UserServiceImp userService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserServiceImp userService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<String> login(@RequestBody User user){

        Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String name = authentication.getName();
        String token = tokenService.generateToken(authentication);
       boolean b = authentication.isAuthenticated();
        LOG.info("authenticated: " + b);
        LOG.info("TOKEN: " + token);
        LOG.info(" 2 token: " + authentication.getPrincipal().toString());

       return new ResponseEntity<>("TOKEN: " + token, HttpStatus.OK);
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User u = userService.addUser(user);
       return new ResponseEntity<>(u, HttpStatus.OK);

    }

    @GetMapping(value = "/hola")
    public String hola(){
        return "Hola todo bien ! ";
    }


    }


