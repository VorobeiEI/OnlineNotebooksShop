package bussinessprocesses.command.otherPages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.implementation.PasswordGeneratorImpl;
import bussinessprocesses.interfaces.PasswordGenerator;
import bussinessprocesses.mailing.MailThread;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.UserDAOImpl;
import dao.interfaces.UserDAO;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class ForgotPasswordPageCommand implements ActionCommand {
    private static final String PARAM_USER_EMAIL = "email";
    private static final String PARAM_BACK_TO_INDEX = "back";
    private static final String SUBJECT_EMAIL = "Your password";
    @Override
    public String execute(HttpServletRequest request) {
        UserDAO userDAO = new UserDAOImpl();
        PasswordGenerator pg = new PasswordGeneratorImpl();
        String userEmail = request.getParameter(PARAM_USER_EMAIL);

        if(request.getParameter(PARAM_BACK_TO_INDEX) != null) {
            return ConfigurationManager.getProperty("path.page.index");
        } else {
            //check for registration user if it's not print cause on the web page
            boolean uniqueMail = userDAO.checkMail(userEmail);
            if (!uniqueMail || userEmail.isEmpty()) {
                request.setAttribute("errorMailNotRegisteredMessage", MessagesManager.getProperty("message.usernotregeistered"));
                return ConfigurationManager.getProperty("path.page.forgotpassword");
            }
            //if ok, generate new password for user and send it to email
            String newPassword = pg.generateTemporaryPassword();
            String passwordhash = pg.calculateHash(newPassword);
            userDAO.updatePassword(userEmail, passwordhash);

            MailThread mailOperator = new MailThread(userEmail, SUBJECT_EMAIL, newPassword);
            // start process send mail in new thread
            mailOperator.start();
            // redirect to forgotpassword page
            request.setAttribute("newPasswordSending", MessagesManager.getProperty("message.newpasswordsending"));
            return ConfigurationManager.getProperty("path.page.forgotpassword");
        }
    }
}
