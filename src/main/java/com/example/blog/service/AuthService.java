package com.example.blog.service;

import com.example.blog.enumeration.Role;
import com.example.blog.model.AuthenticationRequest;
import com.example.blog.model.AuthenticationResponse;
import com.example.blog.model.RegistrationRequest;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest user) {
        log.info("Got new user of name: {}", user.getUsername());

        var newUser = User
                .builder()
                .firstName(user.getFirstName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .isEnabled(Boolean.TRUE)
                .build();
        log.info("saving user {}", newUser.getFirstName());
        repository.save(newUser);
        log.info("contruction of user {}", newUser.getFirstName());

        return AuthenticationResponse.builder()
                .firstname(newUser.getFirstName())
                .lastname(newUser.getLastname())
                .username(newUser.getUsername())
                .token(jwtService.generateToken(newUser))
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("User wants to sign in: {}", request.getUsername());
        log.info("Authenticating: {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        log.info("Finding user: {}", request.getUsername());
        var user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (!user.getFirstName().isEmpty()){
            log.info("Found: {}", request.getUsername());
            log.info("Returning user: {}", request.getUsername());
            return AuthenticationResponse.builder()
                    .firstname(user.getFirstName())
                    .lastname(user.getLastname())
                    .username(user.getUsername())
                    .token(jwtService.generateToken(user))
                    .build();
        }else{
            throw new UsernameNotFoundException("User not found");
        }



    }

}
