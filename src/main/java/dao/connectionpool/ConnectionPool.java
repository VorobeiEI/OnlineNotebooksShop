package dao.connectionpool;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jacksparrow on 21.09.17.
 */
public class ConnectionPool {
    private static final String DATASOURCE_NAME = "jdbc/Online_Shop";

    private static DataSource dataSource;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);


    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        }catch (NamingException e){
            logger.error(e.getMessage(), e);
        }
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public static void close(Statement st){
        try {
            if (st != null) {
                st.close();
            }
        }catch (SQLException e){
            logger.error("ERROR: cannot close statement " + e);
        }
    }

    public static void close(Connection conn){
        try{
            if(conn!=null && conn.getAutoCommit()){
                conn.close();
            }
        }catch (SQLException e){
            logger.error("ERROR: cannot close connection " + e);
        }
    }
}
