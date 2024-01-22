package com.limonnana.be01.repository;

import com.limonnana.be01.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User findUserByEmail(String email){
        User user = new User();
        user.setUsername("rosenzvaig@gmail.com");
        user.setPassword("avocado1");
        return user;
    }
}
