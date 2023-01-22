package com.example.blog.model;


import com.example.blog.enumeration.HouseCategory;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_propertyrate")
public class PropertyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String town;
    private HouseCategory category;
    private String gpsaddress;
    private String location;
    private String streetname;
    private String landmark;
    private Double amountown;
}
