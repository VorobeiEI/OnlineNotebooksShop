package bussinessprocesses.implementation;

import bussinessprocesses.interfaces.PasswordGenerator;
import bussinessprocesses.interfaces.UserLogic;
import dao.impl.UserDAOImpl;
import dao.interfaces.UserDAO;
import entity.users.User;
import entity.users.UserStatus;
import entity.users.UserType;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class UserLogicImpl implements UserLogic{
    private static final Logger logger = Logger.getLogger(UserLogicImpl.class);
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config.properties");
    private UserDAO userDAO = new UserDAOImpl();


    @Override
    public User createUser(String email, String name, String passHash, String phone, String role, UserStatus status) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPasswordHash(passHash);
        user.setPhone(phone);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }

    @Override
    public void initDefaultUser() {

        logger.info("Checking inital users setup");
        try {
            userDAO.getUserByEmail(resourceBundle.getString("admin.mail"));
        } catch (UserDAOImpl.UserNotFoundException ex) {
            logger.info("Creating default admin user");

            PasswordGenerator pg = new PasswordGeneratorImpl();
            String email = resourceBundle.getString("admin.mail");
            String name = resourceBundle.getString("admin.name");
            String passHash = pg.calculateHash(resourceBundle.getString("admin.pass"));
            String phone = resourceBundle.getString("admin.phone");
            String role = String.valueOf(UserType.ADMINISTRATOR);
            UserStatus status = UserStatus.STATUS_OK;

            User user = createUser(email, name, passHash, phone, role, status);
            userDAO.createUser(user);
        }
    }
}
