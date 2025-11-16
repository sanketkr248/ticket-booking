package com.example.ticket_booking.controller;


import com.example.ticket_booking.dto.request.LoginRequest;
import com.example.ticket_booking.dto.request.RegisterRequest;
import com.example.ticket_booking.dto.response.AuthResponse;
import com.example.ticket_booking.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)
    {
        authenticationService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Register Successfully!!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        AuthResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }
}
