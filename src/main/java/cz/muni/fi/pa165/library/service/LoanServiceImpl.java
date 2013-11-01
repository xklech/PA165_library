package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.exceptions.CustomerDaoException;
import cz.muni.fi.pa165.library.exceptions.LoanDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.to.CustomerTO;
import cz.muni.fi.pa165.library.to.LoanTO;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Michal Sukupčák
 */
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanDao loanDao;
    
    @Autowired
    private CustomerDao customerDao;

    @Override
    public void addLoan(LoanTO loanTo) {
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	try {
	    this.loanDao.addLoan(loan);
	} catch (LoanDaoException ex) {
	    Logger.getLogger(LoanServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	    throw new ServiceDataAccessException("addLoan",ex);
	}
    }

    @Override
    public void updateLoan(LoanTO loanTo) {
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	try {
	    this.loanDao.updateLoan(loan);
	} catch (LoanDaoException ex) {
	    Logger.getLogger(LoanServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	    throw new ServiceDataAccessException("updateLoan",ex);
	}
    }

    @Override
    public void deleteLoan(LoanTO loanTo) {
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	try {
	    this.loanDao.deleteLoan(loan);
	} catch (LoanDaoException ex) {
	    Logger.getLogger(LoanServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	    throw new ServiceDataAccessException("deleteLoan",ex);
	}
    }

    @Override
    public LoanTO findLoanById(Long id) {
	try {
	    Loan loan = this.loanDao.findLoanById(id);
	    return EntityConvertor.convertFromLoan(loan);
	} catch (LoanDaoException ex) {
	    Logger.getLogger(LoanServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	    throw new ServiceDataAccessException("findLoanById",ex);
	}
    }

    @Override
    public List<LoanTO> findAllActiveLoans() {
	Collection<Loan> loans = this.loanDao.findAllActiveLoans();
	List<LoanTO> loanTos = new ArrayList();
	for (Loan loan : loans) {
	    loanTos.add(EntityConvertor.convertFromLoan(loan));
	}
	return loanTos;
    }

    @Override
    public List<LoanTO> findLoansByCustomer(CustomerTO customerTo) {
	if (customerTo == null) {
	    throw new ServiceDataAccessException("Parameter customerTo can't be null!");
	} else if (customerTo.getId() == null) {
	    throw new ServiceDataAccessException("Object customerTo supplied as parameter must have a non-null parameter id!");
	}
	try {
	    Customer customer = this.customerDao.findCustomerById(customerTo.getId());
	    Collection<Loan> loans = this.loanDao.findLoansByCustomer(customer);
	    List<LoanTO> loanTos = new ArrayList();
	    for (Loan loan : loans) {
		loanTos.add(EntityConvertor.convertFromLoan(loan));
	    }
	    return loanTos;
	} catch (CustomerDaoException | LoanDaoException ex) {
	    Logger.getLogger(LoanServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
	    throw new ServiceDataAccessException("findLoansByCustomer",ex);
	}
    }

    @Override
    public List<LoanTO> findLoansByFromTo(Date fromDate, Date toDate) {
	Collection<Loan> loans = this.loanDao.findLoansByFromTo(fromDate,toDate);
	List<LoanTO> loanTos = new ArrayList();
	for (Loan loan : loans) {
	    loanTos.add(EntityConvertor.convertFromLoan(loan));
	}
	return loanTos;
    }
    
}
