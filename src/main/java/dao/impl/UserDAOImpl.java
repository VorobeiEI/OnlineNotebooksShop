package dao.impl;

import dao.connectionpool.ConnectionPool;
import dao.exception.DatabaseException;
import dao.interfaces.UserDAO;
import entity.users.User;
import entity.users.UserStatus;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by jacksparrow on 21.09.17.
 */
public class UserDAOImpl implements UserDAO {
    public static final String SQL_SELECT_USER_STATUS = "SELECT status FROM User WHERE email=?";
    public static final String SQL_UPDATE_STATUS = "UPDATE User SET status = ? WHERE email=?";
    public static final String SQL_SELECT_USERS_BY = "SELECT * FROM User WHERE ";
    public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM User";
    public static final String SQL_CHECK_EMAIL_IN_DB ="SELECT email FROM User WHERE email=?";
    public static final String SQL_SELECT_USER_BY_MAIL ="SELECT id, name, cr_date, email, password, status, phone, role FROM User WHERE email = ?";
    public static final String SQL_CREATE_USER = "INSERT INTO User (name, cr_date, email, password, status, phone, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_USER_BY_MAIL = "DELETE FROM User WHERE email = ?";
    public static final String SQL_UPDATE_PASSWORD = "UPDATE User SET password = ? WHERE email=?";
    public static final String SQL_UPDATE_USER = "UPDATE User SET name = ?, password = ?, phone = ? , email = ? WHERE email=?";

    private static  final Logger logger = Logger.getLogger(UserDAOImpl.class);
    public static final class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(final String email){
            super("User with email: " + email + " hasn't been found");
        }
        public UserNotFoundException(final String element, final String elementValue){
            super("User with " + element + ": " + elementValue + " hasn't been found");
        }
    }

    @Override
    public boolean checkMail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean findMail = false;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_CHECK_EMAIL_IN_DB);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            findMail = rs.next();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }

        return findMail;
    }

    @Override
    public User getUserByEmail(String email) {

        Connection conn = null;
        PreparedStatement ps = null;
        User user = null;

        try {
            conn = ConnectionPool.getConnection();

            ps = conn.prepareStatement(SQL_SELECT_USER_BY_MAIL);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setCreationDate(new java.util.Date(
                        rs.getDate(3).getTime()
                ));
                user.setEmail(rs.getString(4));
                user.setPasswordHash(rs.getString(5));
                user.setStatus(UserStatus.valueOf(rs.getString(6)));
                user.setPhone(rs.getString(7));
                user.setRole(rs.getString(8));
                return user;
            }else {
                throw new UserNotFoundException(email);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);

        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }


    }

    @Override
    public void createUser(User user) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_CREATE_USER);

            ps.setString(1, user.getName());
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getStatus().toString());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getRole());
            ps.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);

        } finally {

            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public ArrayList<User> getAllUSers()    {
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<User> allUsers = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL_USERS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setCreationDate(new java.util.Date(
                        rs.getDate(3).getTime()
                ));
                user.setEmail(rs.getString(4));
                user.setPasswordHash(rs.getString(5));
                user.setStatus(UserStatus.valueOf(rs.getString(6)));
                user.setPhone(rs.getString(7));
                user.setRole(rs.getString(8));
                allUsers.add(user);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
        return allUsers;
    }

    @Override
    public void deleteUserByEmail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_DELETE_USER_BY_MAIL);
            ps.setString(1, email);
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public void updatePassword(String email, String password) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_PASSWORD);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.execute();

        } catch (SQLException e) {

        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
        logger.info("Password changed successfuly for User with emal: "  + email);

    }

    @Override
    public User getUserByElement(String element, String elementValue)   {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_USERS_BY + element + "=?");
            ps.setString(1, elementValue);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setCreationDate(new java.util.Date(
                        rs.getDate(3).getTime()
                ));
                user.setEmail(rs.getString(4));
                user.setPasswordHash(rs.getString(5));
                user.setStatus(UserStatus.valueOf(rs.getString(6)));
                user.setPhone(rs.getString(7));
                user.setRole(rs.getString(8));
                return user;
            }else {
                throw new UserNotFoundException(element, elementValue);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }

    }

    @Override
    public void changeStatus(String email) {

        Connection conn = null;
        PreparedStatement ps = null;
        UserStatus currentStatus = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_USER_STATUS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentStatus = UserStatus.valueOf(rs.getString("status"));
            }
            ps.close();
            ps = conn.prepareStatement(SQL_UPDATE_STATUS);
                if (currentStatus.equals(UserStatus.STATUS_OK)) {
                ps.setString(1, UserStatus.STATUS_BLOCKED.name());
                ps.setString(2, email);
                ps.execute();
                logger.info("Account with email " + email + " blocked");
            } else {
                ps.setString(1, UserStatus.STATUS_OK.name());
                ps.setString(2, email);
                logger.info("Account with email " + email + " activate");
                ps.execute();
                }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);

        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public void update(User user, String email) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_USER);

            ps.setString(1, user.getName());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, email);
            ps.execute();
            logger.info("Information about user: " + user + " updated");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
    }
}
