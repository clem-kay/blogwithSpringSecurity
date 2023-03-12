package com.example.blog.service.impl;

import com.example.blog.model.Blog;
import com.example.blog.model.User;
import com.example.blog.repository.BlogPostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.BlogService;
import com.example.blog.service.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class BlogServiceImpl implements BlogService {
    private final BlogPostRepository blogRepository;
    private final UserRepository userRepository;

    private final JWTService jwtService;

    @Override
    public Blog create(Blog blog, String authorization) {
         log.info("Saving new blog {}", blog.getTitle());
        var newOwner = Blog
                .builder()
                .title(blog.getTitle())
                .details(blog.getDetails())
                .createdby(getUser(authorization))
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
        return blogRepository.save(newOwner);
    }

    @Override
    public Collection<Blog> list(int start, int limit) {
        log.info("retriving all blogs");
        return blogRepository.findAll(PageRequest.of(start,limit)).toList();
    }

    @Override
    public Blog get(Long id) {
        log.info("getting owner of id {}", id);
        Optional<Blog> user = blogRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @Override
    public Blog update(Blog blog,String authorization) {
        log.info("updating property owner {}", blog.getTitle());
        blog.setUpdatedAt(new Date());
        blog.setCreatedby(getUser(authorization));
        return blogRepository.save(blog);
    }

    @Override
    public Boolean delete(Long id) {
         blogRepository.deleteById(id);
         return Boolean.TRUE;
    }

    User getUser(String authorization){
        log.info("getting the token from the authorization header");
        String token = authorization.substring(7);
        log.info("extracting the username from the token");
        String username = jwtService.extractUsername(token);
        log.info("Extracted the username, Got {}",username);
        log.info("getting the user from the token");
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
