package com.example.blog.model.request;

import com.example.blog.enumeration.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyOwnerRequest {
    private String firstname;
    private String lastname;
    private String email;
    private int telephone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
