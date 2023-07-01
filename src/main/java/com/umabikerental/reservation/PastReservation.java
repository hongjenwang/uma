package com.umabikerental.reservation;

import com.umabikerental.bike.Bike;
import com.umabikerental.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "past_reservations")
public class PastReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bike_id", nullable = false)
    private Bike bike;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime reservationDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    // Add other relevant fields as needed

    public PastReservation() {}

    public PastReservation(Bike bike, User user, LocalDateTime reservationDate, LocalDateTime returnDate) {
        this.bike = bike;
        this.user = user;
        this.reservationDate = reservationDate;
        this.returnDate = returnDate;
    }
}
