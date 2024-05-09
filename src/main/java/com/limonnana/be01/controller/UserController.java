package com.limonnana.be01.controller;


import com.limonnana.be01.entity.User;
import com.limonnana.be01.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/getuserbyusername/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username){
    User user = userService.getUserByUsername(username);
    return user;
    }

}
