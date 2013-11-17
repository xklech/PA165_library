package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;

import cz.muni.fi.pa165.library.exceptions.LoanDaoException;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * Implementation of LoanDao interface.
 *
 * @author Michal Sukupčák
 */
@Repository
public class LoanDaoImpl implements LoanDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void addLoan(Loan loan) throws LoanDaoException {
	if (loan == null) {
	    throw new LoanDaoException("addLoan: loan parameter can't be null!");
	}
	if (loan.getId() != null) {
	    throw new LoanDaoException("addLoan: loan *can't* have a non-null id parameter!");
	}
	this.entityManager.persist(loan);
    }

    @Override
    public void updateLoan(Loan loan) throws LoanDaoException {
	if (loan == null) {
	    throw new LoanDaoException("updateLoan: loan parameter can't be null!");
	}
	if (loan.getId() == null) {
	    throw new LoanDaoException("updateLoan: loan *must* have a non-null id parameter!");
	}
	Loan loanToUpdate = this.entityManager.find(Loan.class,loan.getId());
	if (loanToUpdate == null) {
	    throw new LoanDaoException("updateLoan: loan with given id *doesn't* exist in database!");
	}
	loanToUpdate.setCustomer(loan.getCustomer());
	loanToUpdate.setImpression(loan.getImpression());
	loanToUpdate.setFromDate(loan.getFromDate());
	loanToUpdate.setToDate(loan.getToDate());
	loanToUpdate.setDamageType(loan.getDamageType());
    }

    @Override
    public void deleteLoan(Loan loan) throws LoanDaoException {
	if (loan == null) {
	    throw new LoanDaoException("deleteLoan: parameter loan can't be null!");
	}
	if (loan.getId() == null) {
	    throw new LoanDaoException("deleteLoan: loan *must* have a non-null id parameter!");
	}
	Loan loanToDelete = this.entityManager.find(Loan.class,loan.getId());
	if (loanToDelete == null) {
	    throw new LoanDaoException("deleteLoan: loan with given id *doesn't* exist in database!");
	}
	this.entityManager.remove(loanToDelete);
    }

    @Override
    public Loan findLoanById(Long id) throws LoanDaoException {
	if (id == null) {
	    throw new LoanDaoException("findLoanById: parameter id can't be null!");
	}
	return this.entityManager.find(Loan.class,id);
    }

    @Override
    public Collection<Loan> findAllActiveLoans() {
	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
	CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);
	cq.where(cb.isNull(cq.from(Loan.class).get("toDate")));
	TypedQuery q = this.entityManager.createQuery(cq);
	return q.getResultList();
    }

    @Override
    public Collection<Loan> findLoansByCustomer(Customer customer) throws LoanDaoException {
	if (customer == null) {
	    throw new LoanDaoException("findLoansByCustomer: parameter customer can't be null!");
	}
	if (customer.getId() == null) {
	    throw new LoanDaoException("findLoansByCustomer: customer's id can't be null!");
	}
	TypedQuery q = this.entityManager.createQuery("SELECT l FROM Loan l LEFT JOIN l.customer c WHERE c.id = :id",Loan.class);
	q.setParameter("id",customer.getId());
	return q.getResultList();
    }

    @Override
    public Collection<Loan> findLoansByFromTo(Date fromDate, Date toDate) {
	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
	CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);
	Root r = cq.from(Loan.class);
	if (fromDate == null && toDate == null) {
	    cq.select(r);
	} else {
	    if (fromDate == null) {
		cq.where(cb.lessThanOrEqualTo(r.get("toDate"),toDate));
	    } else if (toDate == null) {
		cq.where(cb.greaterThanOrEqualTo(r.get("fromDate"),fromDate));
	    } else {
		cq.where(cb.greaterThanOrEqualTo(r.get("fromDate"),fromDate),cb.lessThanOrEqualTo(r.get("toDate"),toDate));
	    }
	}
	TypedQuery q = this.entityManager.createQuery(cq);
	return q.getResultList();
    }
    
}
