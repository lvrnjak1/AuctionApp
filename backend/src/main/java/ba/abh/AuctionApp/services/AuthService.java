package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.auth.JwtProvider;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.RoleName;
import ba.abh.AuctionApp.exceptions.custom.EmailInUseException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCredentialsException;
import ba.abh.AuctionApp.repositories.UserRepository;
import ba.abh.AuctionApp.requests.RegisterRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtProvider jwtProvider;

    public AuthService(final PasswordEncoder passwordEncoder,
                       final UserRepository userRepository,
                       final RoleService roleService,
                       final JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
    }

    public User register(final RegisterRequest registerRequest) {
        if (userExists(registerRequest.getEmail())) {
            throw new EmailInUseException(String.format("Email %s is already in use", registerRequest.getEmail()));
        }

        String password = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User(
                registerRequest.getName(),
                registerRequest.getSurname(),
                registerRequest.getEmail(),
                password
        );
        user.addRole(roleService.findByRoleName(RoleName.ROLE_BUYER));
        userRepository.save(user);
        return user;
    }

    private boolean userExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User loadUserByEmail(final String email) {
        return loadUserByUsername(email);
    }

    @Override
    public User loadUserByUsername(final String s) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(s)
                .orElseThrow(
                        () -> new InvalidCredentialsException(String.format("Email %s isn't associated with an account", s))
                );
    }

    public String authenticate(final String email, final String password) {
        UserDetails userDetails = loadUserByEmail(email);
        boolean correctPassword = passwordEncoder.matches(password, userDetails.getPassword());
        if (!correctPassword) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        return jwtProvider.generateToken(userDetails);
    }
}
