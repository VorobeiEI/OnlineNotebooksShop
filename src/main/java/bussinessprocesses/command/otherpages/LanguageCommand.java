package bussinessprocesses.command.otherpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by jacksparrow on 03.10.17.
 */
public class LanguageCommand implements ActionCommand{

    private static final String PARAM_LOCALE_RU = "ru";
    private static final String RU = "RU";
    private static final String PARAM_LOCALE_EN = "en";
    private static final String EN = "US";

    @Override
    public String execute(HttpServletRequest request) {

        // setting locale
        if (request.getParameter(PARAM_LOCALE_RU) != null) {
            setLocale(request, PARAM_LOCALE_RU, RU);
        } else {
            setLocale(request, PARAM_LOCALE_EN, EN);
        }

        return ConfigurationManager.getProperty("path.page.index");
    }

    private void setLocale(HttpServletRequest request, String language, String place){
        Locale locale = new Locale(language, place);
        request.getSession().setAttribute("locale", locale);
    }
}
