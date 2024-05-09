package com.limonnana.be01.service;

import com.limonnana.be01.entity.User;
import com.limonnana.be01.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {


    public User addUser(User user);

    public Optional<User> userLogin(User user);

    public List<User> getUsers();

    public User getUserByUsername(String username);

    public User saveUser(User user);


}
