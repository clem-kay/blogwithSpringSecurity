package com.example.blog.service;

import com.example.blog.model.Blog;

import java.util.Collection;

public interface BlogService {
    Blog create(Blog blog, String authorization);

    Collection<Blog> list(int start, int limit);
    Blog get(Long id);
    Blog update(Blog blog, String authorization);
    Boolean delete(Long id);
}
