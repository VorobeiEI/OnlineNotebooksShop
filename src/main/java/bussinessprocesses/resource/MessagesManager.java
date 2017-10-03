package bussinessprocesses.resource;

import java.util.ResourceBundle;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class MessagesManager {

    private final static ResourceBundle resourceBundle =
            ResourceBundle.getBundle("messages");
    private MessagesManager(){
    }

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
