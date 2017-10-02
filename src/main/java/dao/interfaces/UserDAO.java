package dao.interfaces;

import entity.users.User;
import entity.users.UserStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksparrow on 21.09.17.
 */
public interface UserDAO {

    public boolean checkMail(String email);

    public User getUserByEmail(String email);

    public void createUser(User user);

    public ArrayList<User> getAllUSers();

    public void deleteUserByEmail(String email);

    public void updatePassword (String email, String password);

    public User getUserByElement(String element, String elementValue);

    public void changeStatus(String email);

    public void update (User user, String email);
}
