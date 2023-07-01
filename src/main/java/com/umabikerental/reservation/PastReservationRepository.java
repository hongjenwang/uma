package com.umabikerental.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastReservationRepository extends JpaRepository<PastReservation, Long> {
    // You can define custom query methods here if needed.
}
