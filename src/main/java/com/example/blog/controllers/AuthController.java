package com.example.blog.controllers;

import com.example.blog.exception.RecordNotFoundException;
import com.example.blog.model.request.AuthenticationRequest;
import com.example.blog.model.response.AuthenticationResponse;
import com.example.blog.model.RegistrationRequest;
import com.example.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> reqister(
            @RequestBody RegistrationRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request){
        try{
            AuthenticationResponse response = authenticationService.authenticate(request);

            return ResponseEntity.ok(response);

        }catch(ResponseStatusException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username or Password incorrect");
        }

    }
}
