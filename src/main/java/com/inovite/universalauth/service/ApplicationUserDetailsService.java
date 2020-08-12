package com.inovite.universalauth.service;

import com.inovite.universalauth.model.ApplicationUserDetails;
import com.inovite.universalauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inovite.universalauth.entity.User;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //return new User("foo", "foo", new ArrayList<>());
        Optional<User> user = userRepository.findByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
        return user.map(ApplicationUserDetails::new).get();
    }
}
