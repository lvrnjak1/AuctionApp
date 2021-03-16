package ba.abh.AuctionApp.services;

import ba.abh.AuctionApp.domain.Token;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class EmailService {
    @Value("${spring.sendgrid.sender}")
    private String sender;
    @Value("${spring.sendgrid.reset-url}")
    private String resetUrl;

    private final SendGrid sendGrid;
    private final TemplateEngine templateEngine;

    public EmailService(final SendGrid sendGrid, final TemplateEngine templateEngine) {
        this.sendGrid = sendGrid;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMail(final Mail mail) {
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ignored) {

        }
    }

    @Async
    public void sendPasswordResetMail(final Token token) {
        Email fromMail = new Email(sender);
        Email toMail = new Email(token.getUser().getEmail());
        String subject = "Auction App: Reset Password";
        Content content = new Content("text/html", generateMailContent(token));
        Mail mail = new Mail(fromMail, subject, toMail, content);
        sendMail(mail);
    }

    public String generateMailContent(final Token token) {
        Context context = new Context();
        context.setVariable("reset_url", String.format("%s%s", resetUrl, token.getToken()));
        return templateEngine.process("reset_pw_email", context);
    }
}
