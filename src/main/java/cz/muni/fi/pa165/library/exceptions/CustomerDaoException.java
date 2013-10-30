package cz.muni.fi.pa165.library.exceptions;

/**
 * Exception thrown from CustomerDAO indicates problem with manipulating Customer
 * 
 * @author Michal Sukupčák
 */
public class CustomerDaoException extends Exception {

    /**
     * Creates a new instance of
     * <code>CustomerDAOException</code> without detail message.
     */
    public CustomerDaoException() {}

    /**
     * Constructs an instance of
     * <code>CustomerDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerDaoException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>CustomerDAOException</code> with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of exception
     */
    public CustomerDaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
