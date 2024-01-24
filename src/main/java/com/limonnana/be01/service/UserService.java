package com.limonnana.be01.service;

import com.limonnana.be01.entity.User;
import com.limonnana.be01.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {


    public User addUser(User user);

    public Optional<User> userLogin(User user);

    public List<User> getUsers();
}
