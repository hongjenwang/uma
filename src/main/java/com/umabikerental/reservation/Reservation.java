package com.umabikerental.reservation;

import com.umabikerental.bike.Bike;
import com.umabikerental.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservation_id_sequence",
            sequenceName = "reservation_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_id_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modified_at;

    public Reservation() {}

    public Reservation(Bike bike, User user, LocalDateTime start_date, LocalDateTime end_date, Integer quantity) {
        this.bike = bike;
        this.user = user;
        this.start_date = start_date;
        this.end_date = end_date;
        this.quantity = quantity;
    }
}
