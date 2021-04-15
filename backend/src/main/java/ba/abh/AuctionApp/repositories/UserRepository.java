package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndActive(final String email, final boolean active);

    default Optional<User> findByEmail(final String email) {
        return findByEmailAndActive(email, true);
    }
}
