package ba.abh.AuctionApp.utility.card;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ExpirationYearValidator implements ConstraintValidator<ValidExpirationYear, Integer> {
    @Override
    public boolean isValid(final Integer year, final ConstraintValidatorContext constraintValidatorContext) {
        if (year == null){
            return false;
        }

         int currentYear = LocalDate.now().getYear();
        return year >= currentYear && year <= currentYear + 4;
    }
}
