package Util;

import java.util.regex.Pattern;

public class emailValidator {

    // Regex aprimorado: não permite “..”, exige TLD de pelo menos 2 letras
    private static final String EMAIL_REGEX =
        "^(?!.*\\\\.\\\\.)[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(?:\\\\.[A-Za-z]{2,})+$";
    private static final Pattern PATTERN =
        Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        return PATTERN.matcher(email).matches();
    }
}