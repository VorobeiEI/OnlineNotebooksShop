package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.implementation.PasswordGeneratorImpl;
import bussinessprocesses.implementation.UserLogicImpl;
import bussinessprocesses.implementation.VerificationImpl;
import bussinessprocesses.interfaces.PasswordGenerator;
import bussinessprocesses.interfaces.UserLogic;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.UserDAOImpl;
import dao.interfaces.UserDAO;
import entity.users.User;
import entity.users.UserStatus;
import entity.users.UserType;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class RegistrationPageCommand implements ActionCommand {
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_PASSWORD_COMFIRM = "passwordC";
    private static final String PARAM_NAME_FNAME = "firstName";
    private static final String PARAM_NAME_PHONE = "phone";
    private VerificationImpl varification = new VerificationImpl();
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        PasswordGenerator generator = new PasswordGeneratorImpl();
        UserDAO userManagementDAO = new UserDAOImpl();
        UserLogic userManagementBP = new UserLogicImpl();
        String page = null;
        String passHash = null;

        //extract values from user request
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String passC = request.getParameter(PARAM_NAME_PASSWORD_COMFIRM);
        String name = request.getParameter(PARAM_NAME_FNAME);
        String phone = request.getParameter(PARAM_NAME_PHONE);

        //check that all fields aren't null
        if (email.isEmpty() || pass.isEmpty() || passC.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            request.setAttribute("errorFieldNullMessage", MessagesManager.getProperty("message.fieldnull"));
            return ConfigurationManager.getProperty("path.page.registration");
        }

        //check for unique of email name if it's not unique print cause on the web page
        boolean uniqueMail = userManagementDAO.checkMail(email);
        if (uniqueMail) {
            request.setAttribute("errorUniqueMailMessage",
                    MessagesManager.getProperty("message.emailnotunique"));
            return ConfigurationManager.getProperty("path.page.registration");
        }

        //check for equality passwords, if not print cause on the web page
        if (pass.equals(passC)) {
            passHash = generator.calculateHash(pass);
        } else {
            request.setAttribute("errorConfirmPasswordMessage",
                    MessagesManager.getProperty("message.passnotequale"));
            return ConfigurationManager.getProperty("path.page.registration");
        }

        //check for email & phone verification, if not print cause on the web page
        boolean checkEmail = checkEmail(request, email);
        boolean checkPhone = checkPhone(request, phone);
        if (!(checkEmail || checkPhone)) {
            return ConfigurationManager.getProperty("path.page.registration");
        }

        //If all checks passed - create new user
        String role = String.valueOf(UserType.CLIENT);
        User user = userManagementBP.createUser(email, name, passHash, phone, role, UserStatus.STATUS_OK);

        //fill new user into data base
        userManagementDAO.createUser(user);

        request.getSession().setAttribute("role", String.valueOf(UserType.CLIENT));
        request.getSession().setAttribute("user", email);
        page = ConfigurationManager.getProperty("path.page.index");

        return page;
    }

    private boolean checkEmail(HttpServletRequest request, String email){
        if (varification.verificationEmail(email)) {
            return true;
        } else {
            request.setAttribute("errorNotCorrectEmailMessage",
                    MessagesManager.getProperty("message.notcorrectemail"));
            return false;
        }
    }

    private boolean checkPhone(HttpServletRequest request, String phone){
        if (varification.verificationPhone(phone)) {
            return true;
        } else {
            request.setAttribute("errorNotCorrectPhoneMessage",
                    MessagesManager.getProperty("message.notcorrectphone"));
            return false;
        }
    }
}

