package com.example.blog.service;

import com.example.blog.model.PropertyOwner;
import com.example.blog.model.User;
import com.example.blog.model.request.PropertyOwnerRequest;

import java.util.Collection;
import java.util.Optional;

public interface PropertyOwnerService {
    PropertyOwner create(PropertyOwnerRequest propertyOwner, String authorization);
    Collection<PropertyOwner> list(int start,int limit);
    PropertyOwner get(Long id);
    PropertyOwner update(PropertyOwner propertyOwner);
    Boolean delete(Long id);
}
