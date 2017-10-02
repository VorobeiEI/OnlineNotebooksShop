package bussinessprocesses.command.otherPages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.MailManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class MailPageCommand implements ActionCommand {
    private static final String PARAM_MESSAGE = "message";
    private static final String PARAM_SUBJECT = "subject";
    private static final String PARAM_TO = MailManager.getProperty("mail.user.sendto");
    private static final String SPACE = ", ";
    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
