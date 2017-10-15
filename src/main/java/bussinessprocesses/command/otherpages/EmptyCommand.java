package bussinessprocesses.command.otherpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import entity.users.UserType;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {

        //In case of an error or a direct appeal to the controller
        //redirects to the page corresponding to the user role
        if(request.getSession().getAttribute("role") == String.valueOf(UserType.ADMINISTRATOR)) {
            return ConfigurationManager.getProperty("path.page.admin");
        }
        if(request.getSession().getAttribute("role") == String.valueOf(UserType.CLIENT)) {
            return ConfigurationManager.getProperty("path.page.main");
        } else {
            return ConfigurationManager.getProperty("path.page.index");
        }
    }

}

