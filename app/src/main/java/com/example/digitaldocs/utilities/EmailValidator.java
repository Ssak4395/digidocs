package com.example.digitaldocs.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    /**
     * Regex used to check if Email is valid.
     */
    private Pattern pattern;
    private Matcher matcher;

    public static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    public boolean validate(String emailStr) {
        System.out.println(emailStr);
        matcher = pattern.matcher(emailStr);
        return matcher.find();
    }
}
