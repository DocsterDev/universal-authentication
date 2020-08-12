package com.inovite.universalauth.controller;

import com.inovite.universalauth.entity.User;
import com.inovite.universalauth.model.AuthenticationRequest;
import com.inovite.universalauth.model.AuthenticationResponse;
import com.inovite.universalauth.model.UserSignUp;
import com.inovite.universalauth.service.ApplicationUserDetailsService;
import com.inovite.universalauth.service.SignUpService;
import com.inovite.universalauth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/user")
    public String user(){
        return "Hello User";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin";
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody UserSignUp signUp) {
        return signUpService.signUpUser(signUp);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
