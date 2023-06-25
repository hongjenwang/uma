package com.umabikerental.reservation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public Reservation createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        return reservationService.createReservation(reservationRequest);
    }

    @GetMapping
    public List<Reservation> getReservationsByPage(Pageable pageable) {
        return reservationService.getReservationsByPage(pageable);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/by-bike/{bikeId}")
    public ResponseEntity<List<Reservation>> getReservationsByBike(@PathVariable Long bikeId) {
        List<Reservation> reservations = reservationService.getReservationsByBike(bikeId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBike(
            @RequestParam("bikeId") Long bikeId,
            @RequestParam("userId") Long userId,
            @RequestParam("returnDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime returnDate) {

        reservationService.returnBike(bikeId, userId, returnDate);
        return ResponseEntity.ok("Bike returned successfully.");
    }


    @DeleteMapping("/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
    }
}
