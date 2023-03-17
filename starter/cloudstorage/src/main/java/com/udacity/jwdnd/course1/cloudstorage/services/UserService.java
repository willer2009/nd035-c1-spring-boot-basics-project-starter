package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapping.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private UserMapper userMapper;
    private HashService hashService;

    public User getUser(String username){
        return this.userMapper.getUser(username);
    }

    public boolean isUsernameStillAvailable(String Username){
        return (Objects.isNull(getUser(Username)));
    }

    public Integer createUser(User user){
        return userMapper.insert(createLocalUserFromUser(user));
    }

    private User createLocalUserFromUser(User user) {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        String base64EncodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassWord = hashService.getHashedValue(user.getPassword(), base64EncodedSalt);
        return new User(null, user.getUsername(), base64EncodedSalt, hashedPassWord, user.getFirstname(), user.getLastname());
    }

}
