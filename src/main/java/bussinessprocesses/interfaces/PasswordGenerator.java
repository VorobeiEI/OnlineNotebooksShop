package bussinessprocesses.interfaces;

/**
 * Created by jacksparrow on 01.10.17.
 */
public interface PasswordGenerator {

    public String calculateHash(String password);

    public boolean isValidPassword(String password, String hash);

    public String generateTemporaryPassword();
}
