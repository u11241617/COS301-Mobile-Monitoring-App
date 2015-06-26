package the5concurrentnodes.account;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})$";

    /**
     * Checks for empty input fields
     * @param input
     * @return false if input field is not empty and true if input field is empty.
     */
    public static boolean isEmpty(String input) {

        return (input != null && input.trim().length() > 0 ? false : true);
    }

    public static boolean validateEmail(final String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validatePassword(final String password) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
