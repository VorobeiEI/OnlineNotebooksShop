package dao.exception;

/**
 * Created by jacksparrow on 29.09.17.
 */
public final class DatabaseException extends RuntimeException{
    public DatabaseException(Exception e) {
        super(e);
    }
}
