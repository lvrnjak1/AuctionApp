package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUserAndTypeAndUser_Active(final User user, final TokenType tokenType, final boolean active);

    default List<Token> findAllByUserAndType(final User user, final TokenType tokenType) {
        return findAllByUserAndTypeAndUser_Active(user, tokenType, true);
    }

    Optional<Token> findByTokenAndTypeAndUser_Active(final UUID token, final TokenType tokenType, final boolean active);

    default Optional<Token> findByTokenAndType(final UUID token, final TokenType tokenType) {
        return findByTokenAndTypeAndUser_Active(token, tokenType, true);
    }
}
