package ba.abh.AuctionApp.controllers;

import ba.abh.AuctionApp.requests.PaymentRequest;
import ba.abh.AuctionApp.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> completePayment(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        String chargeId = paymentService.charge(paymentRequest);
        return chargeId != null ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment couldn't be processed." +
                        "Check card details and try again.");
    }
}
