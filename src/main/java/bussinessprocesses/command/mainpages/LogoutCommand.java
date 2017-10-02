package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.index");
        // destroy session
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        request.getSession().invalidate();
        request.getSession().setAttribute("locale", locale);
        return page;
    }
}
