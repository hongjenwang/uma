package com.umabikerental.bike;

import com.umabikerental.exception.ResourceNotFoundException;
import com.umabikerental.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public Bike createBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + id));
    }

    public long getAvailableBikes(Long bikeId, LocalDateTime start_date, LocalDateTime end_date) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + bikeId));
        long reservedBikes = reservationRepository.getTotalReservedBikes(bikeId, start_date, end_date);
        return bike.getQuantity() - reservedBikes;
    }

    public void deleteBikeById(Long id) {
        bikeRepository.deleteById(id);
    }
}
