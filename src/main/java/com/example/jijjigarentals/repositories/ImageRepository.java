package com.example.jijjigarentals.repositories;

import com.example.jijjigarentals.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    List<Image> findByPropertyId(UUID propertyId);
}
