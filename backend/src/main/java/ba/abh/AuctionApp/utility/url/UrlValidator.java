package ba.abh.AuctionApp.utility.url;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {
    private static final String URL_PATTERN =
            "^((http|https):\\/\\/)(www.)?[a-zA-Z0-9@:%._\\+~#?&\\/\\/=]" +
                    "{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&\\/\\/=]*)$";
    @Override
    public boolean isValid(final String url, final ConstraintValidatorContext constraintValidatorContext) {
        if (url == null) {
            return true;
        }

        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
