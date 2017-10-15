package bussinessprocesses.command.otherpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.implementation.PasswordGeneratorImpl;
import bussinessprocesses.interfaces.PasswordGenerator;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.UserDAOImpl;
import dao.interfaces.UserDAO;
import entity.users.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class EditProfilePageCommand implements ActionCommand {
    private static final String PARAM_NAME_USER_NAME = "name";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PHONE = "phone";
    private static final String PARAM_NAME_CURRENT_PASSWORD = "currentPassword";
    private static final String PARAM_NAME_NEW_PASSWORD = "newPassword";

    private UserDAO userDao;
    private PasswordGenerator generator = new PasswordGeneratorImpl();

    @Override
    public String execute(HttpServletRequest request) {
        userDao = new UserDAOImpl();
        String page = null;

        //extract values from user request
        String currentPassword = request.getParameter(PARAM_NAME_CURRENT_PASSWORD);

        if(currentPassword.isEmpty()){
            System.out.println("change");
            request.setAttribute("errorPasswordNotCorrect",
                    MessagesManager.getProperty("message.errorpassword"));
            page = ConfigurationManager.getProperty("path.page.editprofile");
        }

        //checking for compliance with login and password
        if (checkingLogin(currentPassword, request)) {
            User user = updateUserProfile(request);
            userDao.update(user, String.valueOf(request.getSession().getAttribute("user")));
            request.getSession().setAttribute("user", user.getEmail());
            page = ConfigurationManager.getProperty("path.page.cabinet");
        }
        return page;
    }

    //method checking equals hashcodes of users input password and password from database
    private boolean checkingLogin(String currentPassword, HttpServletRequest request) {
        PasswordGeneratorImpl generator = new PasswordGeneratorImpl();
        User user = userDao.getUserByEmail(String.valueOf(request.getSession().getAttribute("user")));
        String passwordHash = user.getPasswordHash();
        return generator.isValidPassword(currentPassword, passwordHash);
    }

    private User updateUserProfile(HttpServletRequest request) {
        String userEmail = request.getParameter(PARAM_NAME_EMAIL);
        String newPassword = request.getParameter(PARAM_NAME_NEW_PASSWORD);
        String userName = request.getParameter(PARAM_NAME_USER_NAME);
        String userPhone = request.getParameter(PARAM_NAME_PHONE);

        User user = userDao.getUserByEmail(String.valueOf(request.getSession().getAttribute("user")));

        if(!userName.isEmpty()){
            user.setName(userName);
        }
        if(!userPhone.isEmpty()){
            user.setPhone(userPhone);
        }
        if(!userEmail.isEmpty()){
            //check for unique of email name if it's not unique print cause on the web page
            boolean uniqueMail = userDao.checkMail(userEmail);
            if (uniqueMail) {
                request.setAttribute("errorUniqueMailMessage",
                        MessagesManager.getProperty("message.emailnotunique"));
            } else {
                user.setEmail(userEmail);
            }
        }
        if(!newPassword.isEmpty()) {
            String passHash = generator.calculateHash(newPassword);
            user.setPasswordHash(passHash);
        }

        return user;
    }

}
