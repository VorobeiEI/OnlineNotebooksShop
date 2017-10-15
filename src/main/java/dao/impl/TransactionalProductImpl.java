package dao.impl;

import dao.connectionpool.ConnectionPool;
import dao.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jacksparrow on 29.09.17.
 */
public class TransactionalProductImpl extends ProductDaoImpl {

    private final Connection connection ;
    private static final Logger logger = Logger.getLogger(TransactionalProductImpl.class);


    public TransactionalProductImpl(Connection connection) {
        this.connection = connection;
    }

    public void commit(){
        try{
            connection.commit();
            connection.setAutoCommit(true);
            ConnectionPool.close(connection);
        }catch (SQLException e){
            logger.error(e.getMessage(),e);
            throw new DatabaseException(e);
        }
    }

    public void rollback(){
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            ConnectionPool.close(connection);
            logger.info(this.connection+ " - this transaction rollback");
        }catch (SQLException e){
            logger.error(e.getMessage(),e);
            throw new DatabaseException(e);
        }
    }

    @Override
    protected Connection getConnection() {
        return connection;
    }
}
