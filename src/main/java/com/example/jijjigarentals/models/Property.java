package com.example.jijjigarentals.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String title;
    private String description;

    private BigDecimal pricePerNight;
    private String location;

    @Column(name = "type")
    private String type; // hus, leilighet, rom, hotellrom

    private LocalDateTime createdAt = LocalDateTime.now();
}