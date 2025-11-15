package com.example.ticket_booking.repository;

import com.example.ticket_booking.model.Show;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select s from Show s where s.id = :id")
    Show findByIdForUpdate(@Param("id") Long id);
}
