package com.limonnana.be01.service;

import com.limonnana.be01.entity.User;
import com.limonnana.be01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public User addUser(User user) {
        user = userRepository.saveAndFlush(user);
        return user;
    }


    public Optional<User> userLogin(User user) {
        boolean isLogged = false;
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), userOptional.get().getPassword())) {
                response.add(String.valueOf(userOptional.get().getId()));
                isLogged = true;
            } else {
                response.add("Username or the Password is incorrect");
            }
        } else {
            response.add("Username or the Password is incorrect");
        }
        if(isLogged){
            return userOptional;
        }else{
            return null;
        }

    }


    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUserByUsername(String username){
        User u = userRepository.findByUsername(username).get();
        return  u;
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public User UpdateUser(User user) {
        User u = userRepository.findByUsername(user.getUsername()).get();
        user.setPassword(u.getPassword());
        user.setAdmin(u.isAdmin());
        return userRepository.saveAndFlush(user);
    }


}
