package com.example.jijjigarentals.repositories;

import com.example.jijjigarentals.models.Property;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    List<Property>findByType(String type);
    List<Property>findByLocationContainingIgnoreCase(String location);

}
