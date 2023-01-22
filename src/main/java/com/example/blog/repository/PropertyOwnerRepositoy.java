package com.example.blog.repository;

import com.example.blog.model.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyOwnerRepositoy extends JpaRepository<PropertyOwner, Long> {
}
