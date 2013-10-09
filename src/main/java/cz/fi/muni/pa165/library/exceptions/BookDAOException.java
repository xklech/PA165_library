package cz.fi.muni.pa165.library.exceptions;

/**
 * Exception thrown from BookDAO indicates problem with manipulating Book
 * 
 * @author Jaroslav Klech
 */
public class BookDAOException extends Exception {

    /**
     * Creates a new instance of
     * <code>BookDAOException</code> without detail message.
     */
    public BookDAOException() {
    }

    /**
     * Constructs an instance of
     * <code>BookDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BookDAOException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>BookDAOException</code> with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of exception
     */
    public BookDAOException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
