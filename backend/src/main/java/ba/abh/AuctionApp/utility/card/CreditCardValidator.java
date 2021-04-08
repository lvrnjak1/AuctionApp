package ba.abh.AuctionApp.utility.card;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardValidator implements ConstraintValidator<ValidCreditCard, String> {
    private static final String CARD_NUMBER_PATTERN =
            "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|" +
                    "[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
    @Override
    public boolean isValid(final String cardNumber, final ConstraintValidatorContext constraintValidatorContext) {
        if (cardNumber == null) {
            return false;
        }

        String noSpace = cardNumber.replaceAll("\\s", "");

        Pattern pattern = Pattern.compile(CARD_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(noSpace);
        return matcher.matches();
    }
}
