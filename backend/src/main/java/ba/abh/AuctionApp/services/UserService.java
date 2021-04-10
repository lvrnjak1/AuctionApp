package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.CardDetails;
import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.Gender;
import ba.abh.AuctionApp.domain.enums.TokenType;
import ba.abh.AuctionApp.exceptions.custom.EmailInUseException;
import ba.abh.AuctionApp.exceptions.custom.EmailNotFoundException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCreditCardInfoException;
import ba.abh.AuctionApp.exceptions.custom.InvalidDateException;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.CardDetailsRepository;
import ba.abh.AuctionApp.repositories.UserRepository;
import ba.abh.AuctionApp.requests.CardDetailsRequest;
import ba.abh.AuctionApp.requests.ChangePasswordRequest;
import ba.abh.AuctionApp.requests.UserPatchRequest;
import ba.abh.AuctionApp.utility.JsonNullableUtils;
import ba.abh.AuctionApp.utility.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final CardDetailsRepository cardDetailsRepository;

    public UserService(final UserRepository userRepository,
                       final TokenService tokenService,
                       final PasswordEncoder passwordEncoder,
                       final CardDetailsRepository cardDetailsRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.cardDetailsRepository = cardDetailsRepository;
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

    public User patchUser(final User user, final UserPatchRequest patchRequest) {
        if (patchRequest.getEmail().isPresent()) {
            userRepository.findByEmail(patchRequest.getEmail().get()).ifPresent(u -> {
                if (!u.getId().equals(user.getId())) {
                    throw new EmailInUseException("This email is already associated with an account");
                }
            });
            user.setEmail(patchRequest.getEmail().get());
        }

        if (patchRequest.getName().isPresent()) {
            user.setName(StringUtils.capitalize(patchRequest.getName().get()));
        }

        if (patchRequest.getSurname().isPresent()) {
            user.setSurname(StringUtils.capitalize(patchRequest.getSurname().get()));
        }

        JsonNullableUtils.changeIfPresent(patchRequest.getProfilePhotoUrl(), user::setProfilePhotoUrl);
        JsonNullableUtils.changeIfPresent(patchRequest.getPhoneNumber(), user::setPhoneNumber);

        if (patchRequest.getGender().isPresent()) {
            if (patchRequest.getGender().get() == null) {
                user.setGender(null);
            } else {
                user.setGender(Gender.valueOf(patchRequest.getGender().get().toUpperCase()));
            }
        }

        if (patchRequest.getDateOfBirth().isPresent()) {
            Instant minimumAge = Instant.now(Clock.systemUTC()).minus(18 * 365, ChronoUnit.DAYS);
            Instant dateOfBirth = Instant.ofEpochMilli(patchRequest.getDateOfBirth().get());
            if (dateOfBirth.isBefore(minimumAge)) {
                user.setDateOfBirth(dateOfBirth);
            } else {
                throw new InvalidDateException("You must be at least 18 years old!");
            }
        }

        userRepository.save(user);
        return user;
    }

    public User patchCardDetails(final User user, final CardDetailsRequest cardDetailsRequest) {
        cardDetailsRepository
                .findByCardNumber(cardDetailsRequest.getCardNumber())
                .ifPresent(cardDetails -> {
                            if (user.getCardDetails() == null ||
                                    !user.getCardDetails().getCardNumber().equals(cardDetails.getCardNumber())) {
                                throw new InvalidCreditCardInfoException("Invalid credit card number");
                            }
                        }
                );

        if (user.getCardDetails() == null) {
            CardDetails cardDetails = new CardDetails();
            cardDetails.setNameOnCard(cardDetailsRequest.getNameOnCard());
            cardDetails.setCardNumber(cardDetailsRequest.getCardNumber());
            cardDetails.setExpirationMonth(cardDetailsRequest.getExpirationMonth());
            cardDetails.setExpirationYear(cardDetailsRequest.getExpirationYear());
            cardDetails.setCvc(cardDetailsRequest.getCvc());
            cardDetailsRepository.save(cardDetails);
            user.setCardDetails(cardDetails);
        } else {
            user.getCardDetails().setNameOnCard(cardDetailsRequest.getNameOnCard());
            user.getCardDetails().setCardNumber(cardDetailsRequest.getCardNumber());
            user.getCardDetails().setExpirationMonth(cardDetailsRequest.getExpirationMonth());
            user.getCardDetails().setExpirationYear(cardDetailsRequest.getExpirationYear());
            user.getCardDetails().setCvc(cardDetailsRequest.getCvc());
        }

        return userRepository.save(user);
    }
}
