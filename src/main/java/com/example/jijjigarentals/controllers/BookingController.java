package com.example.jijjigarentals.controllers;

import com.example.jijjigarentals.dtos.BookingRequest;
import com.example.jijjigarentals.models.Booking;
import com.example.jijjigarentals.models.Property;
import com.example.jijjigarentals.models.User;
import com.example.jijjigarentals.repositories.BookingRepository;
import com.example.jijjigarentals.repositories.PropertyRepository;
import com.example.jijjigarentals.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @PostMapping
    public Booking createBooking(@AuthenticationPrincipal User user,
                                 @RequestBody BookingRequest request) {

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProperty(property);
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setStatus(Booking.Status.AVVENTER);

        return bookingRepository.save(booking);
    }

    @GetMapping("/mine")
    public List<Booking> getMyBookings(@AuthenticationPrincipal User user) {
        return bookingRepository.findByUserId(user.getId());
    }
    @PreAuthorize("hasRole('UTLEIER')")
    @GetMapping("/utleier")
    public List<Booking> getBookingsForUtleier(@AuthenticationPrincipal User user) {
        return bookingRepository.findByPropertyOwnerId(user.getId());
    }

    @PostMapping("/{id}/godkjenn")
    public Booking approve(@PathVariable UUID id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(Booking.Status.GODKJENT);
        return bookingRepository.save(booking);
    }

    @PostMapping("/{id}/avslaa")
    public Booking reject(@PathVariable UUID id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(Booking.Status.AVSLAATT);
        return bookingRepository.save(booking);
    }

}