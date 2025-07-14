package com.example.jijjigarentals.repositories;


import com.example.jijjigarentals.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);

    List<Booking> findByPropertyOwnerId(UUID ownerId);
}