package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.TokenType;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void invalidateTokensForUser(final User user, final TokenType tokenType) {
        List<Token> tokens = tokenRepository.findAllByUserAndType(user, tokenType);
        tokenRepository.deleteAll(tokens);
    }

    public Token generateTokenForUser(final User user, final TokenType tokenType) {
        Token token = new Token(UUID.randomUUID().toString(), tokenType, user);
        tokenRepository.save(token);
        return token;
    }

    public Token findByToken(final String token, final TokenType tokenType) {
        return tokenRepository.findByTokenAndType(token, tokenType).orElseThrow(
                () -> new ResourceNotFoundException("Invalid token")
        );
    }

    public boolean isTokenExpired(final Token token) {
        return token
                .getIssuedAt()
                .plus(token.getDurationMin(), ChronoUnit.MINUTES)
                .isBefore(Clock.systemUTC().instant());
    }

    public void invalidateToken(final Token token) {
        tokenRepository.delete(token);
    }
}
