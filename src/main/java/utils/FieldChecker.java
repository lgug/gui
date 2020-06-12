package utils;

import java.util.regex.Pattern;

public class FieldChecker {

    public static final String emptyFieldMsg = "Il campo %s non può essere vuoto!";

    private static Pattern numerablePattern = Pattern.compile("\\d+");
    private static Pattern emailPattern = Pattern.compile("[\\w.]+@\\w+\\.\\w+");
    private static Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");

    public static boolean validateNonEmptyString(String string) {
        return string != null && !string.trim().equals("");
    }

    public static boolean validateNumerableString(String string) {
        return string != null && string.matches(numerablePattern.pattern());
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches(emailPattern.pattern());
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(passwordPattern.pattern());
    }
}
