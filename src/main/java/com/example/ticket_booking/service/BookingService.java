package com.example.ticket_booking.service;

import com.example.ticket_booking.model.Booking;
import com.example.ticket_booking.model.Show;
import com.example.ticket_booking.repository.BookingRepository;
import com.example.ticket_booking.repository.ShowRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowRepository showRepository;

    public Show createShow(String showName, int totalSeats)
    {
        Show show = new Show();
        show.setName(showName);
        show.setTotalSeats(totalSeats);
        return showRepository.save(show);
    }

    @Transactional
    public Show getShowByIdLocked(Long showId)
    {
        Show show = showRepository.findByIdForUpdate(showId);
        if(show == null)
        {
            throw new EntityNotFoundException("Show not found with Id "+showId);
        }
        return show;
    }

    @Transactional
    public Booking bookSeat(Long showId, int seatNumber, String user)
    {
        Show show = getShowByIdLocked(showId);

        if(seatNumber < 1 || seatNumber > show.getTotalSeats()){
            throw new IllegalArgumentException("Invalid seat number for this show.");
        }

        Booking existing = bookingRepository.findByShowIdAndSeatNumber(showId,seatNumber);
        if(existing != null)
        {
            throw new IllegalStateException("Seat already Booked...!");
        }

        Booking booking = new Booking();
        booking.setSeatNumber(seatNumber);
        booking.setUser(user);
        booking.setShow(show);

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForShow(Long showId)
    {
        return bookingRepository.findByShowId(showId);
    }
}
