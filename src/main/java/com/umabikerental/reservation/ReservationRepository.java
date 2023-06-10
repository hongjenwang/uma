package com.umabikerental.reservation;

import com.umabikerental.bike.Bike;
import com.umabikerental.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByBike(Bike bike);
    @Query("""
       SELECT COALESCE(SUM(r.quantity), 0)
       FROM Reservation r
       WHERE r.bike.id = :bikeId AND (r.start_date < :end_date AND r.end_date > :start_date)
       """)
    Integer getTotalReservedBikes(@Param("bikeId") Long bikeId, @Param("start_date") LocalDateTime start_date, @Param("end_date") LocalDateTime end_date);
}

