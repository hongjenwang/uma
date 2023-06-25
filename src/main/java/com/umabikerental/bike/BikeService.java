package com.umabikerental.bike;

import com.umabikerental.exception.ResourceNotFoundException;
import com.umabikerental.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
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

    public List<Bike> getAllAvailableBikes() {
        List<Bike> allBikes = bikeRepository.findAll();
        List<Bike> availableBikes = allBikes.stream()
                .filter(Bike::getIsAvailable)
                .collect(Collectors.toList());
        if (availableBikes.isEmpty()) {
            throw new ResourceNotFoundException("No bikes are currently available.");
        }
        return availableBikes;
    }

    public Bike updateBike(Long id, Bike updatedBike) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + id));

        // Update the bike properties based on the updatedBike object
        bike.setName(updatedBike.getName());
        bike.setQuantity(updatedBike.getQuantity());
        bike.setColor(updatedBike.getColor());
        bike.setCost(updatedBike.getCost());
        bike.setType(updatedBike.getType());
        // Update other properties as needed

        return bikeRepository.save(bike);
    }

    public void deleteBikeById(Long id) {
        bikeRepository.deleteById(id);
    }
}
