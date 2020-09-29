package com.example.digitaldocs.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailValidator {
    /**
     * Regex used to check if Email is valid.
     */

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        System.out.println(emailStr);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
