package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.requests.ChangePasswordRequest;
import ba.abh.AuctionApp.requests.ForgotPasswordRequest;
import ba.abh.AuctionApp.responses.TokenResponse;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class PasswordController {
    private final UserService userService;

    public PasswordController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> handleForgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        Token token = userService.generateOneTimePasswordToken(request.getEmail());
        //send email
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PutMapping("/reset")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request,
                                            @RequestParam String token) {
        userService.resetPasswordForUser(request, token);
        //what about the login token, it is still valid?
        return ResponseEntity.ok().build();
    }
}
