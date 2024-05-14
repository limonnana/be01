package com.limonnana.be01.controller;


import com.limonnana.be01.entity.User;
import com.limonnana.be01.service.UserService;
import io.micrometer.common.util.StringUtils;
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
    user.setPassword("");
    return user;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user){
        User u = new User();
        u.setId(user.getId());
        u.setAdmin(user.isAdmin());
        u.setUsername(user.getUsername());

        if(StringUtils.isNotBlank(user.getName())){
           u.setName(user.getName());
        }
        if(StringUtils.isNotBlank(user.getLastName())){
            u.setLastName(user.getLastName());
        }
        if(StringUtils.isNotBlank(user.getEmail())){
            u.setEmail(user.getEmail());
        }
        if(StringUtils.isNotBlank(user.getPhone())){
            u.setPhone(user.getPhone());
        }
        if(u.getId() != null && u.getId().intValue() > 0) {
            User updatedUser = userService.UpdateUser(u);
            return updatedUser;
        }
        else {
            return u;
        }

    }

}
