package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.domain.enums.TokenType;
import ba.abh.AuctionApp.requests.ChangePasswordRequest;
import ba.abh.AuctionApp.requests.ForgotPasswordRequest;
import ba.abh.AuctionApp.services.EmailService;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/password")
public class PasswordController {
    private final UserService userService;
    private final EmailService emailService;

    public PasswordController(final UserService userService,
                              final EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> handleForgotPassword(@RequestBody @Valid final ForgotPasswordRequest request) {
        Token token = userService.generateOneTimePasswordToken(request.getEmail());
        emailService.sendPasswordResetMail(token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reset")
    public ResponseEntity<?> changePassword(@RequestBody @Valid final ChangePasswordRequest request,
                                            @RequestParam final UUID token) {
        userService.resetPasswordForUser(request, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reset/checkToken")
    public ResponseEntity<?> checkToken(@RequestParam final UUID token) {
        userService.checkIfTokenValid(token, TokenType.ONE_TIME_PASSWORD);
        return ResponseEntity.ok().build();
    }
}
