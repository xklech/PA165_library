package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.CustomerDao;
import cz.muni.fi.pa165.library.dao.LoanDao;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michal Sukupčák
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanDao loanDao;
    
    @Autowired
    private CustomerDao customerDao;

    public void setLoanDao(LoanDao loanDao) {
	this.loanDao = loanDao;
    }
    
    public void setCustomerDao(CustomerDao customerDao) {
	this.customerDao = customerDao;
    }
    
    @Override
    @Secured({"ROLE_ADMIN"})
    public LoanTo addLoan(LoanTo loanTo) {
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	this.loanDao.addLoan(loan);                
	loanTo.setId(loan.getId());
	return loanTo;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public LoanTo updateLoan(LoanTo loanTo) {
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	this.loanDao.updateLoan(loan);
	return loanTo;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public boolean deleteLoan(LoanTo loanTo) {
	Loan loan = EntityConvertor.convertFromLoanTo(loanTo);
	this.loanDao.deleteLoan(loan);
	return true;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public LoanTo findLoanById(Long id) {
	Loan loan = this.loanDao.findLoanById(id);
	return EntityConvertor.convertFromLoan(loan);
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public List<LoanTo> findAllActiveLoans() {
	Collection<Loan> loans = this.loanDao.findAllActiveLoans();
        if (loans == null) {
            return null;
        }
	List<LoanTo> loanTos = new ArrayList();
	for (Loan loan : loans) {
	    loanTos.add(EntityConvertor.convertFromLoan(loan));
	}
	return loanTos;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public List<LoanTo> findLoansByCustomer(CustomerTo customerTo) {
	Customer customer = this.customerDao.findCustomerById(customerTo.getId());
	Collection<Loan> loans = this.loanDao.findLoansByCustomer(customer);
	if (loans == null) {
	    return null;
	}
	List<LoanTo> loanTos = new ArrayList();
	for (Loan loan : loans) {
	    loanTos.add(EntityConvertor.convertFromLoan(loan));
	}
	return loanTos;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    public List<LoanTo> findLoansByFromTo(Date fromDate, Date toDate) {
	Collection<Loan> loans = this.loanDao.findLoansByFromTo(fromDate,toDate);
        if (loans == null) {
            return null;
        }
	List<LoanTo> loanTos = new ArrayList();
	for (Loan loan : loans) {
	    loanTos.add(EntityConvertor.convertFromLoan(loan));
	}
	return loanTos;
    }
    
}
