package Util;

import java.util.regex.Pattern;

public class emailValidator {

    private static final String EMAIL_REGEX =
        "^(?!.*\\.{2})[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$";

    private static final Pattern PATTERN =
        Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean isValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Não permite espaços em branco antes ou depois
        String trimmed = email.trim();
        return PATTERN.matcher(trimmed).matches();
    }
}