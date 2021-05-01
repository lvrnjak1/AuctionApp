package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.User;
import ba.abh.AuctionApp.requests.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Value("${stripe-secret-key}")
    private String stripeSecretKey;
    private final BidService bidService;

    public PaymentService(final BidService bidService) {
        this.bidService = bidService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String charge(final PaymentRequest paymentRequest, final User user) throws StripeException {
        int amountInCents = bidService.getPaymentAmountInCents(paymentRequest.getAuctionId(), user);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amountInCents);
        chargeParams.put("currency", "usd");
        chargeParams.put("source", paymentRequest.getToken());

        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }

}
