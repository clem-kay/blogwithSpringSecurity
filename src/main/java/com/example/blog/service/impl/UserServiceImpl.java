package com.example.blog.service.impl;

import com.example.blog.model.User;
import com.example.blog.model.request.UserRequest;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User create(UserRequest user) {
        log.info("saving a user of name {}", user.getFirstname());
        var newuser = User.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .username(user.getUsername())
                .isEnabled(Boolean.TRUE)
                .password(passwordEncoder.encode("password"))
                .build();
        return userRepository.save(newuser);
    }

    @Override
    public Collection<User> list(int start, int limit) {
        log.info("Retriving all users in the system");
        return userRepository.findAll(PageRequest.of(start,limit, Sort.Direction.ASC)).toList();
    }

    @Override
    public User get(Long id) {
        log.info("Getting user of id {}",id);
        return userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("No user found"));
    }

    @Override
    public User update(User user) {
        log.info("Updating user {}",user.getFirstname());
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(Long id) {
        userRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
