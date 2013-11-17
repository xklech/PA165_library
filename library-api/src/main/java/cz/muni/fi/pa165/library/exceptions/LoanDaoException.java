package cz.muni.fi.pa165.library.exceptions;

/**
 * Exception thrown from LoanDAO indicates problem with manipulating Loan
 * 
 * @author Michal Sukupčák
 */
public class LoanDaoException extends Exception {

    /**
     * Creates a new instance of
     * <code>LoanDAOException</code> without detail message.
     */
    public LoanDaoException() {}

    /**
     * Constructs an instance of
     * <code>LoanDAOException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public LoanDaoException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>LoanDAOException</code> with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause of exception
     */
    public LoanDaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
