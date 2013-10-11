/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Customer;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.entity.Loan_;
import cz.fi.muni.pa165.library.exceptions.LoanDAOException;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of LoanDAO interface.
 *
 * @author Michal Sukupčák
 */
public class LoanDAOImpl implements LoanDAO {

    private EntityManager em;
    
    public LoanDAOImpl(EntityManager em) {
	this.em = em;
    }
    
    @Override
    public void addLoan(Loan loan) throws LoanDAOException {
	if (loan == null) {
	    throw new LoanDAOException("addLoan: loan parameter can't be null!");
	}
	if (loan.getId() != null) {
	    throw new LoanDAOException("addLoan: loan *can't* have a non-null id parameter!");
	}
	this.em.persist(loan);
    }

    @Override
    public void updateLoan(Loan loan) throws LoanDAOException {
	if (loan == null) {
	    throw new LoanDAOException("updateLoan: loan parameter can't be null!");
	}
	if (loan.getId() == null) {
	    throw new LoanDAOException("updateLoan: loan *must* have a non-null id parameter!");
	}
	Loan loanToUpdate = this.em.find(Loan.class,loan.getId());
	if (loanToUpdate == null) {
	    throw new LoanDAOException("updateLoan: loan with given id *doesn't* exist in database!");
	}
	loanToUpdate.setCustomer(loan.getCustomer());
	loanToUpdate.setImpression(loan.getImpression());
	loanToUpdate.setFromDate(loan.getFromDate());
	loanToUpdate.setToDate(loan.getToDate());
	loanToUpdate.setConditionType(loan.getConditionType());
	
    }

    @Override
    public void deleteLoan(Loan loan) throws LoanDAOException {
	if (loan == null) {
	    throw new LoanDAOException("deleteLoan: parameter loan can't be null!");
	}
	if (loan.getId() == null) {
	    throw new LoanDAOException("deleteLoan: loan *must* have a non-null id parameter!");
	}
	Loan loanToDelete = this.em.find(Loan.class,loan.getId());
	if (loanToDelete == null) {
	    throw new LoanDAOException("deleteLoan: loan with given id *doesn't* exist in database!");
	}
	this.em.remove(loanToDelete);
    }

    @Override
    public Loan findLoanById(Long id) throws LoanDAOException {
	if (id == null) {
	    throw new LoanDAOException("findLoanById: parameter id can't be null!");
	}
	return this.em.find(Loan.class,id);
    }

    @Override
    public Collection<Loan> findAllActiveLoans() {
	CriteriaBuilder cb = this.em.getCriteriaBuilder();
	CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);
	cq.where(cb.isNull(cq.from(Loan.class).get(Loan_.toDate)));
	TypedQuery q = this.em.createQuery(cq);
	return q.getResultList();
    }

    @Override
    public Collection<Loan> findLoansByCustomer(Customer customer) throws LoanDAOException {
	if (customer == null) {
	    throw new LoanDAOException("findLoansByCustomer: parameter customer can't be null!");
	}
	if (customer.getId() == null) {
	    throw new LoanDAOException("findLoansByCustomer: customer's id can't be null!");
	}
	TypedQuery q = this.em.createQuery("SELECT l FROM Loan l LEFT JOIN l.customer c WHERE c.id = :id",Loan.class);
	q.setParameter("id",customer.getId());
	return q.getResultList();
    }

    @Override
    public Collection<Loan> findLoansByFromTo(Date fromDate, Date toDate) {
	CriteriaBuilder cb = this.em.getCriteriaBuilder();
	CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);
	Root r = cq.from(Loan.class);
	if (fromDate == null && toDate == null) {
	    cq.select(r);
	} else {
	    if (fromDate == null) {
		cq.where(cb.lessThanOrEqualTo(r.get(Loan_.toDate),toDate));
	    } else if (toDate == null) {
		cq.where(cb.greaterThanOrEqualTo(r.get(Loan_.fromDate),fromDate));
	    } else {
		cq.where(cb.greaterThanOrEqualTo(r.get(Loan_.fromDate),fromDate),cb.lessThanOrEqualTo(r.get(Loan_.toDate),toDate));
	    }
	}
	TypedQuery q = this.em.createQuery(cq);
	return q.getResultList();
    }
    
}
