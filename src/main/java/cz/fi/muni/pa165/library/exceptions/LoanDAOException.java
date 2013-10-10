package cz.fi.muni.pa165.library.exceptions;

/**
 * Exception thrown from LoanDAO indicates problem with manipulating Loan
 * 
 * @author Michal Sukupčák
 */
public class LoanDAOException extends Exception {

    /**
     * Creates a new instance of
     * <code>LoanDAOException</code> without detail message.
     */
    public LoanDAOException() {}

    /**
     * Constructs an instance of
     * <code>LoanDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public LoanDAOException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>LoanDAOException</code> with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of exception
     */
    public LoanDAOException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
