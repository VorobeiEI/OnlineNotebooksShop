package bussinessprocesses.implementation;

import bussinessprocesses.interfaces.PasswordGenerator;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class PasswordGeneratorImpl implements PasswordGenerator {

    private static final String PASSWORD_SALT = "System stability";
    private static final String SECURITY_PASSWORD = "SecurityStabilitySystem";
    private static final int PASSWORD_LENGTH = 8;
    @Override
    public String calculateHash(String password) {
        return String.valueOf((PASSWORD_SALT + password).hashCode())    ;
    }

    @Override
    public boolean isValidPassword(String password, String hash) {
        return calculateHash(password).equals(hash);
    }

    @Override
    public String generateTemporaryPassword() {
        StringBuilder randString = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            randString.append(SECURITY_PASSWORD.charAt((int) (Math.random() * SECURITY_PASSWORD.length())));
        }
        return randString.toString();
    }
}
