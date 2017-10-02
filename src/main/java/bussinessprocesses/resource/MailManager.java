package bussinessprocesses.resource;

import java.util.ResourceBundle;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class MailManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("bussinessprocesses.resources.mail");

    // class extract information from file mail.properties
    private MailManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
