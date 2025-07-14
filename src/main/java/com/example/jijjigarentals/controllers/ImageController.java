package com.example.jijjigarentals.controllers;

import com.example.jijjigarentals.models.Image;
import com.example.jijjigarentals.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/{propertyId}")
    public List<Image> getImagesForProperty(@PathVariable UUID propertyId) {
        return imageRepository.findByPropertyId(propertyId);
    }
}