package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.TokenType;
import ba.abh.AuctionApp.exceptions.custom.EmailNotFoundException;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.UserRepository;
import ba.abh.AuctionApp.requests.ChangePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository,
                       final TokenService tokenService,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(String.format("Email %s isn't associated with an account", email))
        );
    }

    public User findByIdIfExists(final Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id %d doesn't exist", id))
        );
    }

    public User getUserFromPrincipal(final Principal principal) {
        return getUserByEmail(principal.getName());
    }

    public Token generateOneTimePasswordToken(final String email) {
        User user = getUserByEmail(email);
        tokenService.invalidateTokensForUser(user, TokenType.ONE_TIME_PASSWORD);
        return tokenService.generateTokenForUser(user, TokenType.ONE_TIME_PASSWORD);
    }

    public void resetPasswordForUser(final ChangePasswordRequest request, final UUID token) {
        Token t = tokenService.findByToken(token, TokenType.ONE_TIME_PASSWORD);
        if (tokenService.isTokenExpired(t)) {
            throw new ResourceNotFoundException("Invalid token");
        }

        String newPassword = passwordEncoder.encode(request.getPassword());
        t.getUser().setPassword(newPassword);
        userRepository.save(t.getUser());
        tokenService.invalidateToken(t);
    }

    public void checkIfTokenValid(final UUID token, final TokenType type) {
        Token t = tokenService.findByToken(token, type);
        if (tokenService.isTokenExpired(t)) {
            throw new ResourceNotFoundException("Invalid token");
        }
    }
}
