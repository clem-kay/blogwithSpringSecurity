package com.example.blog.service;

import com.example.blog.model.User;
import com.example.blog.model.request.UserRequest;

import java.util.Collection;

public interface UserService {
    User create(UserRequest user);
    Collection<User> list(int start, int limit);
    User get(Long id);
    User update(User user);
    Boolean delete(Long id);
}
