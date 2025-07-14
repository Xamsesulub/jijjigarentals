package com.example.jijjigarentals.controllers;

import com.example.jijjigarentals.models.Image;
import com.example.jijjigarentals.models.Property;
import com.example.jijjigarentals.repositories.ImageRepository;
import com.example.jijjigarentals.repositories.PropertyRepository;
import com.example.jijjigarentals.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

    @RestController
    @RequestMapping("/api/properties")
    public class PropertyController {

        @Autowired
        private PropertyRepository propertyRepository;

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ImageRepository imageRepository;

        // GET alle boliger
        @GetMapping
        public List<Property> getAllProperties() {
            return propertyRepository.findAll();
        }

        // GET én bolig med id
        @GetMapping("/{id}")
        public Property getPropertyById(@PathVariable UUID id) {
            return propertyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bolig ikke funnet"));
        }

        // POST - legg til ny bolig
        @PostMapping
        public Property createProperty(@RequestBody Map<String, String> data) {
            UUID ownerId = UUID.fromString(data.get("ownerId"));
            var user = userRepository.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Eier ikke funnet"));

            Property property = new Property();
            property.setOwner(user);
            property.setTitle(data.get("title"));
            property.setDescription(data.get("description"));
            property.setPricePerNight(new java.math.BigDecimal(data.get("pricePerNight")));
            property.setLocation(data.get("location"));
            property.setType(data.get("type"));

            return propertyRepository.save(property);
        }

        // Søk etter type (hus, rom, etc.)
        @GetMapping("/type/{type}")
        public List<Property> getByType(@PathVariable String type) {
            return propertyRepository.findByType(type);
        }

        // Søk etter location
        @GetMapping("/location/{location}")
        public List<Property> getByLocation(@PathVariable String location) {
            return propertyRepository.findByLocationContainingIgnoreCase(location);
        }

        // DELETE - fjern bolig
        @DeleteMapping("/{id}")
        public void deleteProperty(@PathVariable UUID id) {
            propertyRepository.deleteById(id);
        }
        @PostMapping("/{id}/images")
        public String uploadImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) throws IOException {
            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Eiendom ikke funnet"));

            // Lag mappe hvis den ikke finnes
            String folder = "uploads/";
            Files.createDirectories(Paths.get(folder));

            // Lag unik filnavn
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(folder, filename);
            Files.write(filepath, file.getBytes());

            // Lagre i database
            Image image = new Image();
            image.setProperty(property);
            image.setUrl("/uploads/" + filename);
            imageRepository.save(image);

            return "Lastet opp: " + image.getUrl();
        }



    }

