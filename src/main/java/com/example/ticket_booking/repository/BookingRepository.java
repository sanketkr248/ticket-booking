package com.example.ticket_booking.repository;

import com.example.ticket_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByShowIdAndSeatNumber(Long showId, int seatNumber);
    List<Booking> findByShowId(Long showId);

}
