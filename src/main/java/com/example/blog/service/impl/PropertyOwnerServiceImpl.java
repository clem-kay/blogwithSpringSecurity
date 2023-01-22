package com.example.blog.service.impl;

import com.example.blog.model.PropertyOwner;
import com.example.blog.model.User;
import com.example.blog.model.request.PropertyOwnerRequest;
import com.example.blog.repository.PropertyOwnerRepositoy;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.JWTService;
import com.example.blog.service.PropertyOwnerService;
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
public class PropertyOwnerServiceImpl implements PropertyOwnerService {
    private final PropertyOwnerRepositoy propertyOwnerRepositoy;
    private final UserRepository userRepository;

    private final JWTService jwtService;

    @Override
    public PropertyOwner create(PropertyOwnerRequest propertyOwner, String authorization) {
        log.info("getting the token from the authorization header");
        String token = authorization.substring(7);
        log.info("extracting the username from the token");
        String username = jwtService.extractUsername(token);
        log.info("Extracted the username, Got {}",username);
        log.info("getting the user from the token");
        User addedUser = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        log.info("Saving new property {}", propertyOwner.getFirstname());
        var newOwner = PropertyOwner
                .builder()
                .firstname(propertyOwner.getFirstname())
                .lastname(propertyOwner.getLastname())
                .email(propertyOwner.getEmail())
                .gender(propertyOwner.getGender())
                .telephone(propertyOwner.getTelephone())
                .createdby(addedUser)
                .updatedAt(new Date())
                .createdAt(new Date())

                .build();
        return propertyOwnerRepositoy.save(newOwner);
    }

    @Override
    public Collection<PropertyOwner> list(int start, int limit) {
        log.info("retriving new property");
        return propertyOwnerRepositoy.findAll(PageRequest.of(start,limit)).toList();
    }

    @Override
    public PropertyOwner get(Long id) {
        log.info("getting owner of id {}", id);
        Optional<PropertyOwner> user = propertyOwnerRepositoy.findById(id);
        if (user.isPresent()){
            return user.get();
        }else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @Override
    public PropertyOwner update(PropertyOwner propertyOwner) {
        log.info("updating property owner {}", propertyOwner.getFirstname());
        propertyOwner.setUpdatedAt(new Date());
        return propertyOwnerRepositoy.save(propertyOwner);
    }

    @Override
    public Boolean delete(Long id) {
         propertyOwnerRepositoy.deleteById(id);
         return Boolean.TRUE;
    }
}
