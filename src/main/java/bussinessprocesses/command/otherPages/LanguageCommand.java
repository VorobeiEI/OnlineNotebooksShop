package bussinessprocesses.command.otherPages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by jacksparrow on 03.10.17.
 */
public class LanguageCommand implements ActionCommand{

    private static final String PARAM_LOCALE_RU = "ru_RU";
    private static final String PARAM_LOCALE_EN = "en_US";

    @Override
    public String execute(HttpServletRequest request) {

        // setting locale
        if (request.getParameter(PARAM_LOCALE_RU) != null) {
            setLocale(request, PARAM_LOCALE_RU);
        } else {
            setLocale(request, PARAM_LOCALE_EN);
        }

        return ConfigurationManager.getProperty("path.page.index");
    }

    private void setLocale(HttpServletRequest request, String localeParam){
        Locale locale = new Locale(localeParam);
        request.getSession().setAttribute("locale", locale);
    }
}
