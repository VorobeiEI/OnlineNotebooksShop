package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.implementation.PasswordGeneratorImpl;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.UserDAOImpl;
import dao.interfaces.UserDAO;
import entity.users.User;
import entity.users.UserType;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class LoginPageCommand implements ActionCommand {
    private static final String PARAM_NAME_REGISTRATION = "registration";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_FORGOT_PASSWORD = "forgotPassword";

    private UserDAO userDAO;
    @Override
    public String execute(HttpServletRequest request) {

        userDAO = new UserDAOImpl();
        String page;

        //extract values from user request
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        //redirect to registration page
        if (request.getParameter(PARAM_NAME_REGISTRATION) != null) {
            return ConfigurationManager.getProperty("path.page.registration");
        }

        //redirect to forgotpassword page
        if (request.getParameter(PARAM_NAME_FORGOT_PASSWORD) != null) {
            return ConfigurationManager.getProperty("path.page.forgotpassword");
        }

        //check for registration user if it's not unique print cause on the web page
        boolean uniqueMail = userDAO.checkMail(email);
        if (!uniqueMail) {
            request.setAttribute("errorMailNotRegisteredMessage",
                    MessagesManager.getProperty("message.usernotregeistered"));
            page = ConfigurationManager.getProperty("path.page.login");
            return page;
        }
        //checking for compliance with login and password
        if (checkingLogin(email, pass)) {
            request.getSession().setAttribute("user", email);
            User user = userDAO.getUserByEmail(email);
            String role = user.getRole();

            //check Administrator role and if administrator redirect it to admin page
            if(role.equals(String.valueOf(UserType.ADMINISTRATOR))) {
                request.getSession().setAttribute("role", String.valueOf(UserType.ADMINISTRATOR));
                return ConfigurationManager.getProperty("path.page.admin");
            }

            //with success consumer login redirect to main page
            request.getSession().setAttribute("role", role);
            page = ConfigurationManager.getProperty("path.page.index");
        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessagesManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }

    //method checking equals hashcodes of users input password and password from database
    private boolean checkingLogin(String email, String pass) {
        PasswordGeneratorImpl generator = new PasswordGeneratorImpl();
        User user = userDAO.getUserByEmail(email);
        String passwordHash = user.getPasswordHash();
        return generator.isValidPassword(pass, passwordHash);
    }
}
