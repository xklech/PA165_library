package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.LoanDao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;

import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import cz.muni.fi.pa165.library.exceptions.CustomerDaoException;
import cz.muni.fi.pa165.library.exceptions.LoanDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccesException;

import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTO;
import cz.muni.fi.pa165.library.to.LoanTO;

import cz.muni.fi.pa165.library.utils.EntityConvertor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Ivana Haraslínová
 */
public class CustomerServiceImpl implements CustomerService {
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Autowired
    private LoanDao loanDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setLoanDao(LoanDao loanDao) {
        this.loanDao = loanDao;
    }
    
    @Transactional
    @Override
    public void addCustomer(CustomerTO customerTO) {
        try {
            Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
            customerDao.addCustomer(customer);
            customerTO.setId(customer.getId());
        } catch (CustomerDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("Save customer",ex);
        }
    }

    @Override
    public void deleteCustomer(CustomerTO customerTO) {
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        try {
            customerDao.deleteCustomer(customer);
        } catch (CustomerDaoException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("Delete customer",ex);
        }        
    }

    @Override
    public void updateCustomer(CustomerTO customerTO) {
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTO);
        try {
           customerDao.updateCustomer(customer);
        } catch (CustomerDaoException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("Update customer",ex);
        }
    }

    @Override
    public CustomerTO findCustomerById(Long id) {
        Customer customer = null;
        try{
            customer = customerDao.findCustomerById(id);
        } catch (CustomerDaoException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findCustomerById",ex);
        }
        return EntityConvertor.convertFromCustomer(customer);
    }

    @Override
    public Collection<CustomerTO> findAllCustomers() {
        Collection <Customer> customers = customerDao.findAllCustomers(); 
        
        List<CustomerTO> customersTO = new ArrayList<>();
        for(Customer customer : customers){
            customersTO.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customersTO;
    }

    @Override
    public Collection<CustomerTO> findCustomersByBook(BookTo bookTO) {
        if(bookTO == null){
            throw new ServiceDataAccesException("findCustomerByBook: book is null");
        }
        if(bookTO.getId() == null){
            throw new ServiceDataAccesException("findCustomerByBook: book's id is null");
        }
        Collection <Customer> customers;
        try {
            Book book = bookDao.findBookById(bookTO.getId());
            customers = customerDao.findCustomersByBook(book); 
        } catch (CustomerDaoException | BookDaoException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findCustomerByBook",ex);
        }        
        List<CustomerTO> customersTO = new ArrayList<>();
        for(Customer customer : customers){
            customersTO.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customersTO;
    }

    @Override
    public Collection<CustomerTO> findCustomerByLoan(LoanTO loanTO) {
        if(loanTO == null){
            throw new ServiceDataAccesException("findCustomerByLoan: loan is null");
        }
        if(loanTO.getId() == null){
            throw new ServiceDataAccesException("findCustomerByLoan: loan's id is null");
        }
        Collection <Customer> customers;
        try {
            Loan loan = loanDao.findLoanById(loanTO.getId());
            customers = customerDao.findCustomerByLoan(loan); 
        } catch (CustomerDaoException | LoanDaoException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findCustomerByLoan",ex);
        }        
        List<CustomerTO> customersTO = new ArrayList<>();
        for(Customer customer : customers){
            customersTO.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customersTO;
    }

    @Override
    public Collection<CustomerTO> findCustomerByName(String firstName, String lastName) {
        Collection<Customer> customers;
        try {
            customers = customerDao.findCustomerByName(firstName, lastName);
        } catch (CustomerDaoException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findCustomerByName",ex);
        }
        if(customers == null){
            return null;
        }
        List<CustomerTO> customersTO = new ArrayList<>();
        for(Customer customer : customers){
            customersTO.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customersTO;
    }
    
}
