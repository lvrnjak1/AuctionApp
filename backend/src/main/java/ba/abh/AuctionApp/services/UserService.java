package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
