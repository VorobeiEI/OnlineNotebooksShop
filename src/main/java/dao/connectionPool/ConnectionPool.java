package dao.connectionPool;

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

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env/jdbc/Online_Shop");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        }catch (NamingException e){
            e.printStackTrace();
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

        }
    }

    public static void close(Connection conn){
        try{
            if(conn!=null){
                conn.close();
            }
        }catch (SQLException e){

        }
    }
}