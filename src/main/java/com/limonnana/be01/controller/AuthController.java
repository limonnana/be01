package com.limonnana.be01.controller;


import com.limonnana.be01.entity.LoginDto;
import com.limonnana.be01.entity.ResponseApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.limonnana.be01.entity.User;
import com.limonnana.be01.service.TokenService;
import com.limonnana.be01.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Value( "${mail.admin}" )
    private String mailAdmin;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
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
    public ResponseEntity<User> login(@RequestBody User user){

        Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String name = authentication.getName();
        User u = userService.getUserByUsername(name);
        u.setPassword("");
        String token = tokenService.generateToken(authentication);
       boolean b = authentication.isAuthenticated();
        LOG.info("authenticated: " + b);
        LOG.info(" 2 token: " + authentication.getPrincipal().toString());

        u.setToken(token);

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<User> entity = new ResponseEntity<User>(u,headers,HttpStatus.OK);

        return entity;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User u = userService.addUser(user);
       return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @RequestMapping(value = "setAdmin", method = RequestMethod.GET)
    public String setAdmin(){
        String result = "SUCCESS";
        try {
            if (this.mailAdmin != null) {
                User u = userService.getUserByUsername(this.mailAdmin);
                u.setAdmin(true);
                userService.saveUser(u);
            }
        }catch(Exception e){
            LOG.info(e.getMessage());
            LOG.info("mailAdmin is NULL ");
            result = " FAILED";
            }
        return result;
    }


    @GetMapping(value = "/hola")
    public String hola(){
        return "Hola todo bien ! ";
    }


    }


