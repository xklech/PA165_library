package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Michal Sukupčák
 */
public interface LoanService {
    
    /**
     * Saves loan supplied as parameter into database.
     * 
     * @param loanTo loan to be saved
     * @throws LoanDaoException thrown when loan parameter is null, or has id
     *				with non-null value
     */
    public void addLoan(LoanTo loanTo);
    
    /**
     * Updates data of loan supplied as parameter in database.
     * 
     * @param loanTo loan to be updated
     * @throws LoanDaoException thrown when loan parameter is null or loan has
				null id parameter or loan with given id doesn't
				exist in database
     */
    public void updateLoan(LoanTo loanTo);
    
    /**
     * Deletes loan supplied as parameter from database.
     * 
     * @param loanTo loan to be deleted
     * @throws LoanDaoException thrown when loan parameter is null or loan has
				null id parameter or loan with given id doesn't
				exist in database
     */
    public void deleteLoan(LoanTo loanTo);
    
    /**
     * Finds and returns loan according to its id
     * 
     * @param id id of loan to be returned
     * @return loan with given id
     * @throws LoanDaoException thrown when id parameter is null
     */
    public LoanTo findLoanById(Long id);
    
    /**
     * Finds and returns all active loans (loans who's toDate parameter value is
     * equal to null).
     * 
     * @return loan active loans
     */
    public List<LoanTo> findAllActiveLoans();
    
    /**
     * Finds and returns all loans assigned to customer supplied in parameter.
     * 
     * @param customerTo customer who's loans are looked up
     * @return loan loans assigned to given customer
     * @throws LoanDaoException thrown when customer or customer's id null
     */
    public List<LoanTo> findLoansByCustomer(CustomerTo customerTo);
    
    /**
     * Finds and returns all loans limited satisfying fromDate and toDate
     * limitations.
     * 
     * Method allows null values as parameters. Null value represents no 
     * limitation on fromDate and/or toDate parameters. (ie.
     * LoanDao.findLoansByFromTo(null,null) returns ALL loans from database.)
     * 
     * @param fromDate date at which loan started
     * @param toDate date at which loan ended (or is going to end)
     * @return loan loans satisfying given parameters
     */
    public List<LoanTo> findLoansByFromTo(Date fromDate, Date toDate);
    
}
