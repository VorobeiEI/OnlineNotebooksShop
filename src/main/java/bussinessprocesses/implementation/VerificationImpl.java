package bussinessprocesses.implementation;

import bussinessprocesses.interfaces.Verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class VerificationImpl implements Verification {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(([a-zA-Z][\\w]*)@[\\w[.]]*\\.+([a-z]{2,6}+))");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{3}-\\d{7}");
    @Override
    public boolean verificationEmail(String email){
        Matcher m = EMAIL_PATTERN.matcher(email);
        return m.matches();
    }

    @Override
    public boolean verificationPhone(String phone){
        Matcher m = PHONE_PATTERN.matcher(phone);
        return m.matches();
    }
}
