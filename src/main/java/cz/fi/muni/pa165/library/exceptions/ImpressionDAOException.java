package cz.fi.muni.pa165.library.exceptions;

/**
 * Exception thrown from ImpressionDAO indicates problem with manipulating Impression
 * 
 * @author Mask
 */
public class ImpressionDAOException extends Exception {

    /**
     * Creates a new instance of
     * <code>ImpressionDAOException</code> without detail message.
     */
    public ImpressionDAOException() {
    }

    /**
     * Constructs an instance of
     * <code>ImpressionDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ImpressionDAOException(String msg) {
        super(msg);
    }
    
    public ImpressionDAOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
