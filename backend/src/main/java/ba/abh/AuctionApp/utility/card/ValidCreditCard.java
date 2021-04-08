package ba.abh.AuctionApp.utility.card;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CreditCardValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCreditCard {
    String message() default "{Invalid credit card}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
