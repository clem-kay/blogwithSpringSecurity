package com.example.blog.model;

import com.example.blog.enumeration.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_propertyowner")
public class PropertyOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private int telephone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne(optional = false)
    private User createdby;

}
