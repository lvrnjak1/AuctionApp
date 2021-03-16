package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Token;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    @Value("${app.mail.fromMail}")
    private String fromMail;
    @Value("${app.mail.apiKey}")
    private String apiKey;
    private final String RESET_URL = "https://lvrnjak-auction-app.herokuapp.com/password/reset?token=";
    private final String TEMPLATE_ID = "d-4690af3c953b43629942fe8c89974abc";

    @Async
    public void sendMail(final Token token) throws IOException {
        Email fromMail = new Email("lamija.vrnjak@gmail.com");
        Email toMail = new Email(token.getUser().getEmail());
        Mail mail = new Mail();
        mail.setFrom(fromMail);
        mail.setTemplateId(TEMPLATE_ID);
        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("reset_url", String.format("%s%s", RESET_URL, token.getToken()));
        personalization.addTo(toMail);
        mail.addPersonalization(personalization);

        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sendGrid.api(request);
    }
}
