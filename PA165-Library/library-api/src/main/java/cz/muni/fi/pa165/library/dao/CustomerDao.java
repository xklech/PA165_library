package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.exceptions.CustomerDaoException;
import java.util.Collection;

/**
 * Data Access Object for managing entity Customer.
 *
 * @author Michal Sukupčák
 */
public interface CustomerDao {
    
    /**
     * Saves new customer.
     * 
     * @param customer to be saved
     * @throws CustomerDaoException thrown when customer parameter is null or 
     *                              has id with non-null value                   
     */
    public void addCustomer(Customer customer) throws CustomerDaoException;
    
    /**
     * Updates given customer.
     * 
     * @param customer to be updated
     * @throws CustomerDaoException thrown when customer parameter is null 
     *                              or id is null
     */
    public void updateCustomer(Customer customer) throws CustomerDaoException;
    
    /**
     * Delets given customer.
     * 
     * @param customer to be deleted
     * @throws CustomerDaoException thrown when customer parameter is null 
     *                              or id is null
     */
    public void deleteCustomer(Customer customer) throws CustomerDaoException;
    
    /**
     * Finds customer with given id.
     * 
     * @param id of searched customer
     * @return customer
     * @throws CustomerDaoException thrown when customers id is null
     */
    public Customer findCustomerById(Long id) throws CustomerDaoException;
    
    /**
     * Finds all customers database.
     * 
     * @return customers
     */
    public Collection<Customer> findAllCustomers();
    
    /**
     * Finds all customers which have borrowed given book
     * 
     * @param book
     * @return customers
     * @throws CustomerDaoException thrown when book parameter is null 
     *                              or its id is null
     */
    public Collection<Customer> findCustomersByBook(Book book) throws CustomerDaoException;
    
    /**
     * Finds customer with given loan
     * 
     * @param loan
     * @return customer
     * @throws CustomerDaoException thrown when loan parameter is null 
     *                              or its id is null
     */
    public Customer findCustomerByLoan(Loan loan) throws CustomerDaoException;
    
    /**
     * Finds customer with given name
     * 
     * @param firstName
     * @param lastName
     * @return customers
     * @throws CustomerDaoException thrown when firstName parameter or lastName 
     *                              parameter is null
     */
    public Collection<Customer> findCustomerByName(String firstName, String lastName) throws CustomerDaoException;
    
}
