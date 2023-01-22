package com.example.blog.service;

import com.example.blog.enumeration.Role;
import com.example.blog.exception.RecordNotFoundException;
import com.example.blog.model.RegistrationRequest;
import com.example.blog.model.User;
import com.example.blog.model.request.AuthenticationRequest;
import com.example.blog.model.response.AuthenticationResponse;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public User userExist(AuthenticationRequest request){
        return repository.findByUsername(request.getUsername()).orElseThrow(() -> new RecordNotFoundException("User Account do not exist"));
    };

    public AuthenticationResponse register(RegistrationRequest user) {
        log.info("Got new user of name: {}", user.getUsername());

        var newUser = User
                .builder()
                .firstname(user.getFirstName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .isEnabled(Boolean.TRUE)
                .build();
        log.info("saving user {}", newUser.getFirstname());
        repository.save(newUser);
        log.info("contruction of user {}", newUser.getFirstname());

        return AuthenticationResponse.builder()
                .firstname(newUser.getFirstname())
                .lastname(newUser.getLastname())
                .username(newUser.getUsername())
                .token(jwtService.generateToken(newUser))
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("User wants to sign in: {}", request.getUsername());

        log.info("Finding user: {}", request.getUsername());

        var user = userExist(request);

        log.info("Authenticating: {}", request.getUsername());

        Authentication userAuth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (!userAuth.isAuthenticated()){
            return AuthenticationResponse.builder()
                    .firstname(null)
                    .lastname(null)
                    .username(null)
                    .token(null)
                    .error("Username or Password incorrect")
                    .build();
        }

        return AuthenticationResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .token(jwtService.generateToken(user))
                .build();
    }


}
