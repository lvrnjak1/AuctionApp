package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.exceptions.custom.ResourceNotFoundException;
import ba.abh.AuctionApp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User findByIdIfExists(final Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id %d doesn't exist", id))
        );
    }

    public User getUserFromPrincipal(final Principal principal) {
        return getUserByEmail(principal.getName());
    }
}
