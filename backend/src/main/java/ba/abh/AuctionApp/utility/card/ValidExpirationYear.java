package ba.abh.AuctionApp.utility.card;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ExpirationYearValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpirationYear {
    String message() default "{Invalid expiration year}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
