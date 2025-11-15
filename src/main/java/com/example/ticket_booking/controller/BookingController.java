package com.example.ticket_booking.controller;

import com.example.ticket_booking.dto.request.BookSeatRequest;
import com.example.ticket_booking.dto.request.CreateShowRequest;
import com.example.ticket_booking.dto.response.BookingResponse;
import com.example.ticket_booking.dto.response.ShowResponse;
import com.example.ticket_booking.model.Booking;
import com.example.ticket_booking.model.Show;
import com.example.ticket_booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/shows")
    public ResponseEntity<ShowResponse> createShow(@RequestBody CreateShowRequest req)
    {
        String name = req.getName();
        int totalSeats = req.getTotalSeats();
        Show show = bookingService.createShow(name,totalSeats);
        ShowResponse response = new ShowResponse();
        response.setId(show.getId());
        response.setName(show.getName());
        response.setTotalSeats(show.getTotalSeats());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/shows/{showId}/book")
    public ResponseEntity<BookingResponse> bookSeat(@PathVariable Long showId, @RequestBody BookSeatRequest req)
    {
        int seatNumber = req.getSeatNumber();
        String user = req.getUser();
        Booking booking = bookingService.bookSeat(showId, seatNumber, user);
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setSeatNumber(booking.getSeatNumber());
        response.setUser(booking.getUser());
        response.setShowId(booking.getShow().getId());
        response.setShowName(booking.getShow().getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/shows/{showId}/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(@PathVariable Long showId)
    {
        List<Booking> bookingsForShow = bookingService.getBookingsForShow(showId);
        List<BookingResponse> responses = new ArrayList<>();
        for (Booking b : bookingsForShow) {
            BookingResponse res = new BookingResponse();
            res.setId(b.getId());
            res.setSeatNumber(b.getSeatNumber());
            res.setUser(b.getUser());
            res.setShowId(b.getShow().getId());
            res.setShowName(b.getShow().getName());
            responses.add(res);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }
}
