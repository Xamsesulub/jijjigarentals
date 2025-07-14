package com.example.jijjigarentals.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.jijjigarentals.models.User;
import com.example.jijjigarentals.models.Property;
import com.example.jijjigarentals.models.Booking;
import com.example.jijjigarentals.repositories.UserRepository;
import com.example.jijjigarentals.repositories.PropertyRepository;
import com.example.jijjigarentals.repositories.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private BookingRepository bookingRepository;

    //  Alle brukere
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Alle eiendommer
    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    //  Alle bookinger
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Slett bruker
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(java.util.UUID.fromString(id));
    }

    // Slett bolig
    @DeleteMapping("/properties/{id}")
    public void deleteProperty(@PathVariable String id) {
        propertyRepository.deleteById(java.util.UUID.fromString(id));
    }

    //  Slett booking
    @DeleteMapping("/bookings/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingRepository.deleteById(java.util.UUID.fromString(id));
    }
}

