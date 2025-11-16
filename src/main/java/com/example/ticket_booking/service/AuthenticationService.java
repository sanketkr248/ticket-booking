package com.example.ticket_booking.service;


import com.example.ticket_booking.dto.request.LoginRequest;
import com.example.ticket_booking.dto.request.RegisterRequest;
import com.example.ticket_booking.dto.response.AuthResponse;
import com.example.ticket_booking.model.User;
import com.example.ticket_booking.repository.UserRepository;
import com.example.ticket_booking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request)
    {
        User user = new User();

        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        AuthResponse res = new AuthResponse();
        res.setToken(token);
        return res;
    }

    public AuthResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String token = jwtUtil.generateToken(request.getUsername());
        AuthResponse res = new AuthResponse();
        res.setToken(token);
        return res;
    }
}
