package com.umabikerental.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {
    @NotNull(message = "bikeId is required")
    private Long bikeId;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "start_date is required")
    private LocalDateTime start_date;

    @NotNull(message = "end_date is required")
    private LocalDateTime end_date;

    @NotNull(message = "quantity is required")
    private Integer quantity;
}