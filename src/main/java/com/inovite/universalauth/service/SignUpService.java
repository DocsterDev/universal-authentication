package com.inovite.universalauth.service;

import com.inovite.universalauth.entity.User;
import com.inovite.universalauth.model.UserSignUp;
import com.inovite.universalauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signUpUser (UserSignUp signUp) {

        Optional<User> userPersistent = userRepository.findByUsername(signUp.getUsername());
        if (userPersistent.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(signUp.getUsername());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setActive(true);
        user.setRoles("ADMIN");
        return userRepository.save(user);
    }

}
