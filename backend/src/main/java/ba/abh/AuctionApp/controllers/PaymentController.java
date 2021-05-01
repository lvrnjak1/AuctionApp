package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.requests.PaymentRequest;
import ba.abh.AuctionApp.services.PaymentService;
import ba.abh.AuctionApp.services.UserService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;

    public PaymentController(final PaymentService paymentService, final UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> completePayment(@RequestBody final PaymentRequest paymentRequest,
                                                  final Principal principal) throws StripeException {
        User user = userService.getUserByEmail(principal.getName());
        String chargeId = paymentService.charge(paymentRequest, user);
        return chargeId != null ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment couldn't be processed." +
                        "Check card details and try again.");
    }
}
