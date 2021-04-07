package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.requests.UserPatchRequest;
import ba.abh.AuctionApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PatchMapping
    @Secured("ROLE_BUYER")
    public ResponseEntity<User> patchMyProfile(final Principal principal,
                                               @RequestBody @Valid UserPatchRequest patchRequest) {
        User user = userService.getUserByEmail(principal.getName());
        User patched = userService.patchUser(user, patchRequest);
        return ResponseEntity.ok(patched);
    }
}
