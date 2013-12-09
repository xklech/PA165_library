package cz.muni.fi.pa165.library.exceptions;

/**
 * Exception thrown from ImpressionDAO indicates problem with manipulating Impression
 * 
 * @author Mask
 */
public class ImpressionDaoException extends Exception {

    /**
     * Creates a new instance of
     * <code>ImpressionDAOException</code> without detail message.
     */
    public ImpressionDaoException() {
    }

    /**
     * Constructs an instance of
     * <code>ImpressionDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ImpressionDaoException(String msg) {
        super(msg);
    }
   
    /**
     * Constructs an instance of
     * <code>ImpressionDAOException</code> with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of exception
     */
    public ImpressionDaoException(String msg, Throwable cause) {
        super(msg,cause);
    }
    
}
