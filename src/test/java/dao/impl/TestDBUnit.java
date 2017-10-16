package dao.impl;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by jacksparrow on 09.10.17.
 */
public class TestDBUnit extends DatabaseTestCase {
    public static final String TABLE_TEST = "test_table";
    private FlatXmlDataSet loadedDataSet;
    ;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Online_Shop","root","root");

        return new DatabaseConnection(jdbcConnection);
    }

    @Override
    protected IDataSet getDataSet() throws Exception {

        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader()
                .getResourceAsStream("tag.xml"));

        return loadedDataSet;
    }

    public void testCheckLoginDataLoaded() throws Exception{
        assertNotNull(loadedDataSet);
        int rowCount = loadedDataSet.getTable(TABLE_TEST).getRowCount();
        assertEquals(1, rowCount);
    }

}
