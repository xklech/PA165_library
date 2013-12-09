package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Ivana Haraslínová
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Autowired
    private LoanDao loanDao;
    
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setLoanDao(LoanDao loanDao) {
        this.loanDao = loanDao;
    }
    
    @Transactional
    @Override
    public CustomerTo addCustomer(CustomerTo customerTo) {
	Customer customer = EntityConvertor.convertFromCustomerTo(customerTo);
	customerDao.addCustomer(customer);
	customerTo.setId(customer.getId());
	return customerTo;
    }

    @Override
    public CustomerTo updateCustomer(CustomerTo customerTo) {
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTo);
       customerDao.updateCustomer(customer);
	return customerTo;
    }
    
    @Override
    public boolean deleteCustomer(CustomerTo customerTo) {
        Customer customer = EntityConvertor.convertFromCustomerTo(customerTo);
	customerDao.deleteCustomer(customer); 
	return true;
    }

    @Override
    public CustomerTo findCustomerById(Long id) {
        Customer customer;
	customer = customerDao.findCustomerById(id);
        return EntityConvertor.convertFromCustomer(customer);
    }

    @Override
    public List<CustomerTo> findAllCustomers() {
        Collection <Customer> customers = customerDao.findAllCustomers(); 
        if (customers == null) {
            return null;
        }
        List<CustomerTo> customerTo = new ArrayList<>();
        for(Customer customer : customers){
            customerTo.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customerTo;
    }

    @Override
    public List<CustomerTo> findCustomersByBook(BookTo bookTo) {
	if (bookTo == null) {
	    throw new IllegalArgumentException("bookTo");
	}
        Collection <Customer> customers;
	Book book = bookDao.findBookById(bookTo.getId());
	customers = customerDao.findCustomersByBook(book); 
        if(customers == null){
            return null;
        }
        List<CustomerTo> customersTo = new ArrayList<>();
        for(Customer customer : customers){
            customersTo.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customersTo;
    }

    @Override
    public CustomerTo findCustomerByLoan(LoanTo loanTo) {
	if (loanTo == null) {
	    throw new IllegalArgumentException("loanTo");
	}
	Customer customer;
	Loan loan = loanDao.findLoanById(loanTo.getId());
	customer = customerDao.findCustomerByLoan(loan);
	return EntityConvertor.convertFromCustomer(customer);
    }

    @Override
    public List<CustomerTo> findCustomerByName(String firstName, String lastName) {
        Collection<Customer> customers;
	customers = customerDao.findCustomerByName(firstName, lastName);
        if (customers == null) {
            return null;
        }
        List<CustomerTo> customersTo = new ArrayList<>();
        for(Customer customer : customers){
            customersTo.add(EntityConvertor.convertFromCustomer(customer));
        }
        return customersTo;
    }
    
}
