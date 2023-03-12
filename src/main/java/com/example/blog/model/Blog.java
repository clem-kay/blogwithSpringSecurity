package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_blogPost")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String details;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne(optional = false)
    private User createdby;

}
