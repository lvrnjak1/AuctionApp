package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.requests.LoginRequest;
import ba.abh.AuctionApp.requests.RegisterRequest;
import ba.abh.AuctionApp.responses.LoginResponse;
import ba.abh.AuctionApp.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody final RegisterRequest registerRequest) {
        User registeredUser = authService.register(registerRequest);
        String jwtToken = authService.authenticate(registerRequest.getEmail(), registerRequest.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(registeredUser, jwtToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody final LoginRequest loginRequest) {
        String jwtToken = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        User user = authService.loadUserByEmail(loginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(user, jwtToken));
    }
}
