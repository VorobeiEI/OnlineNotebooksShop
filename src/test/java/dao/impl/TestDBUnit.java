package dao.impl;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by jacksparrow on 09.10.17.
 */
public class TestDBUnit extends DatabaseTestCase {
    public static final String TABLE_LOGIN = "login";
    private FlatXmlDataSet loadedDataSet;
    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection("" +
                "jdbc:mysql://localhost:3306/Online_Shop","root","root");

        return new DatabaseConnection(jdbcConnection);
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("tag.xml"));

        return loadedDataSet;
    }


}
