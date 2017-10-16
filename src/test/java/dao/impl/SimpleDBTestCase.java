package dao.impl;

import dao.exception.DatabaseException;
import dao.interfaces.UserDAO;
import entity.users.User;
import org.apache.log4j.Logger;
import org.dbunit.Assertion;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.sql.*;

/**
 * Created by jacksparrow on 16.10.17.
 */
public class SimpleDBTestCase extends DatabaseTestCase {
    public static final String SQL_UPDATE_USER = "UPDATE User SET name = ?, password = ?, phone = ? , email = ? WHERE email=?";
    private static  final Logger logger = Logger.getLogger(SimpleDBTestCase.class);

    public void update(User user, String email) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Online_Shop","root","root");

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
    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Online_Shop","root","root");
        Statement stmt = jdbcConnection.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");

        IDatabaseConnection conn = new DatabaseConnection(jdbcConnection);
        DatabaseConfig config = conn.getConfig();

        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new MsSqlDataTypeFactory() );
        return conn;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("current_data.xml"));
    }



    @Test
    public void testUpdateUser() throws Exception {

        User user = new User();
        user.setName("Vorobei");
        user.setPasswordHash("qwerty");
        user.setPhone("0937765839");
        user.setEmail("vorobeiei@gmail.com");

        //UserDAO test = new UserDAOImpl();
        SimpleDBTestCase test = new SimpleDBTestCase();
        test.update(user, "vorobeiei@gmail.com");

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("User");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("expected_data.xml"));

        ITable expectedTable = expectedDataSet.getTable("User");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(
                actualTable, expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }
}
