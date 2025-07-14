package com.example.jijjigarentals.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookingRequest {
    private UUID propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
}
