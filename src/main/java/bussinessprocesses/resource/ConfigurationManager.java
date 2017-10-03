package bussinessprocesses.resource;

import java.util.ResourceBundle;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class ConfigurationManager {

    private final static ResourceBundle resourcesBundle =
            ResourceBundle.getBundle("config");

    private ConfigurationManager(){

    }

    public static String getProperty(String key){
        return resourcesBundle.getString(key);
    }


}
