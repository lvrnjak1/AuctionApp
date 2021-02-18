package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.auth.JwtProvider;
import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.domain.enums.RoleName;
import ba.abh.AuctionApp.exceptions.custom.EmailInUseException;
import ba.abh.AuctionApp.exceptions.custom.InvalidCredentialsException;
import ba.abh.AuctionApp.repositories.UserRepository;
import ba.abh.AuctionApp.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtProvider jwtProvider;

    public User register(RegisterRequest registerRequest) {
        if (userExists(registerRequest.getEmail())){
            throw new EmailInUseException("Email " + registerRequest.getEmail() + " is already in use");
        }

        String password = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User(registerRequest.getName(), registerRequest.getSurname(), registerRequest.getEmail(), password);
        user.addRole(roleService.findByRoleName(RoleName.ROLE_BUYER));
        userRepository.save(user);
        return user;
    }

    private boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public User loadUserByEmail(String email) {
        return loadUserByUsername(email);
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s)
                .orElseThrow(() -> new InvalidCredentialsException("User with email " + s + "doesn't exist"));
    }

    public String authenticate(String email, String password) {
        UserDetails userDetails = loadUserByEmail(email);
        boolean correctPassword = passwordEncoder.matches(password, userDetails.getPassword());
        if(!correctPassword){
            throw new InvalidCredentialsException("Invalid credentials");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);
        return jwtProvider.generateToken(userDetails);
    }
}
