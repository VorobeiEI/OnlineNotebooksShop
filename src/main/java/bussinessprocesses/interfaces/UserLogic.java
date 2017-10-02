package bussinessprocesses.interfaces;

import entity.users.User;
import entity.users.UserStatus;

/**
 * Created by jacksparrow on 02.10.17.
 */
public interface UserLogic {

    public User createUser(String email, String name, String passHash, String phone, String role, UserStatus status);

    public void initDefaultUser();
}
