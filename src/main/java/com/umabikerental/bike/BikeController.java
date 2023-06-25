package com.umabikerental.bike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.umabikerental.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/bikes")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @PostMapping
    public Bike createBike(@RequestBody Bike bike) {
        return bikeService.createBike(bike);
    }

    @GetMapping
    public List<Bike> getBikes() {
        return bikeService.getAllBikes();
    }

    @GetMapping("/{id}")
    public Bike getBike(@PathVariable Long id) {
        return bikeService.getBikeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteBikeById(id);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Long> getAvailableBikes(
            @PathVariable Long id,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start_date,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end_date)
    {
        long availableBikes = bikeService.getAvailableBikes(id, start_date, end_date);
        return ResponseEntity.ok(availableBikes);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Bike>> getAllAvailableBikes() {
        try {
            List<Bike> availableBikes = bikeService.getAllAvailableBikes();
            return new ResponseEntity<>(availableBikes, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable("id") Long id, @RequestBody Bike updatedBike) {
        Bike bike = bikeService.updateBike(id, updatedBike);
        return ResponseEntity.ok(bike);
    }

}
