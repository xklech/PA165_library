package cz.fi.muni.pa165.library.exceptions;

/**
 * Exception thrown from CustomerDAO indicates problem with manipulating Customer
 * 
 * @author Michal Sukupčák
 */
public class CustomerDAOException extends Exception {

    /**
     * Creates a new instance of
     * <code>CustomerDAOException</code> without detail message.
     */
    public CustomerDAOException() {}

    /**
     * Constructs an instance of
     * <code>CustomerDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerDAOException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>CustomerDAOException</code> with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of exception
     */
    public CustomerDAOException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
