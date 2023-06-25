package com.umabikerental.reservation;

import com.umabikerental.bike.Bike;
import com.umabikerental.bike.BikeRepository;
import com.umabikerental.exception.NotEnoughBikesAvailableException;
import com.umabikerental.exception.ResourceNotFoundException;
import com.umabikerental.user.User;
import com.umabikerental.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private UserRepository userRepository;

    public Reservation createReservation(ReservationRequest reservationRequest) {
        Bike bike = bikeRepository.findById(reservationRequest.getBikeId())
                .orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + reservationRequest.getBikeId()));
        User user = userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + reservationRequest.getUserId()));

        LocalDateTime start_date = reservationRequest.getStart_date();
        LocalDateTime end_date = reservationRequest.getEnd_date();
        Integer quantity = reservationRequest.getQuantity();

        // Check bike availability.
        Integer totalReservedBikes = reservationRepository.getTotalReservedBikes(bike.getId(), start_date, end_date);
        if (bike.getQuantity() < (totalReservedBikes + quantity)) {
            throw new NotEnoughBikesAvailableException("Not enough bikes available for the requested period.");
        }

        bike.setIsAvailable(false); // Set isAvailable to false

        Reservation reservation = new Reservation(bike, user, start_date, end_date, quantity);
        Reservation savedReservation = reservationRepository.save(reservation);

        return savedReservation;
    }

    public void returnBike(Long bikeId, Long userId, LocalDateTime returnDate) {
        List<Reservation> reservations = reservationRepository.findByBikeIdAndUserId(bikeId, userId);
        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("No reservation found for bikeId: " + bikeId + " and userId: " + userId);
        }

        Reservation reservation = reservations.get(0);
        reservationRepository.delete(reservation);

        Bike bike = reservation.getBike();
        bike.setIsAvailable(true);
        bikeRepository.save(bike);
    }

    public List<Reservation> getReservationsByPage(Pageable pageable) {
        return reservationRepository.findAll(pageable).toList();
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getReservationsByBike(Long bikeId) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new ResourceNotFoundException("Bike not found with id: " + bikeId));
        return reservationRepository.findByBike(bike);
    }

    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }
}