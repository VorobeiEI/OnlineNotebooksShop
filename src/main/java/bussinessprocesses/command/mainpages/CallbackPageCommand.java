package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.mailing.MailThread;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MailManager;
import bussinessprocesses.resource.MessagesManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class CallbackPageCommand implements ActionCommand {
    private static final String PARAM_MESSAGE = "message";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_CONTACTS = "contacts";
    private static final String PARAM_TO = MailManager.getProperty("mail.user.sendto");
    private static final String SPACE = ", ";
    @Override
    public String execute(HttpServletRequest request) {
        String message = request.getParameter(PARAM_MESSAGE);
        String userName = request.getParameter(PARAM_NAME);
        String userContacts = request.getParameter(PARAM_CONTACTS);

        //check that all fields aren't null
        if(message.isEmpty() || userName.isEmpty() || userContacts.isEmpty()){
            request.setAttribute("errorFieldNullMessage", MessagesManager.getProperty("message.fieldnull"));
            return ConfigurationManager.getProperty("path.page.callback");
        }

        String subject = userName + SPACE + userContacts;

        MailThread mailOperator = new MailThread(PARAM_TO, subject, message);
        // start process send mail in new thread
        mailOperator.start();
        // redirect to cabinet page
        return ConfigurationManager.getProperty("path.page.main");
    }
}
