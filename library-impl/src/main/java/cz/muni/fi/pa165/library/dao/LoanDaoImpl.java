package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.entity.Loan;
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
    public void addLoan(Loan loan) {
	if (loan == null) {
	    throw new IllegalArgumentException("addLoan: loan parameter can't be null!");
	}
	if (loan.getId() != null) {
	    throw new IllegalArgumentException("addLoan: loan *can't* have a non-null id parameter!");
	}
	if (loan.getCustomer() == null) {
	    throw new IllegalArgumentException("addLoan: loan customer *can't* be null!");
	} else if (loan.getCustomer().getId() == null) {
	    this.entityManager.persist(loan.getCustomer());
	}
	if (loan.getImpression() == null) {
	    throw new IllegalArgumentException("addLoan: loan impression *can't* be null!");
	} else if (loan.getImpression().getId() == null) {
	    this.entityManager.persist(loan.getImpression());
	}
	loan.setCustomer(this.entityManager.find(Customer.class,loan.getCustomer().getId()));
	loan.setImpression(this.entityManager.find(Impression.class,loan.getImpression().getId()));
	this.entityManager.persist(loan);
    }

    @Override
    public void updateLoan(Loan loan) {
	if (loan == null) {
	    throw new IllegalArgumentException("updateLoan: loan parameter can't be null!");
	}
	if (loan.getId() == null) {
	    throw new IllegalArgumentException("updateLoan: loan *must* have a non-null id parameter!");
	}
	Loan loanToUpdate = this.entityManager.find(Loan.class,loan.getId());
	if (loanToUpdate == null) {
	    throw new IllegalArgumentException("updateLoan: loan with given id *doesn't* exist in database!");
	}
	if (loan.getCustomer() == null) {
	    throw new IllegalArgumentException("updateLoan: loan customer *can't* be null!");
	} else if (loan.getCustomer().getId() == null) {
	    this.entityManager.persist(loan.getCustomer());
	}
	if (loan.getImpression() == null) {
	    throw new IllegalArgumentException("updateLoan: loan impression *can't* be null!");
	} else if (loan.getImpression().getId() == null) {
	    this.entityManager.persist(loan.getImpression());
	}
	loanToUpdate.setCustomer(this.entityManager.find(Customer.class,loan.getCustomer().getId()));
	loanToUpdate.setImpression(this.entityManager.find(Impression.class,loan.getImpression().getId()));
	loanToUpdate.setFromDate(loan.getFromDate());
	loanToUpdate.setToDate(loan.getToDate());
	loanToUpdate.setDamageType(loan.getDamageType());
    }

    @Override
    public void deleteLoan(Loan loan) {
	if (loan == null) {
	    throw new IllegalArgumentException("deleteLoan: parameter loan can't be null!");
	}
	if (loan.getId() == null) {
	    throw new IllegalArgumentException("deleteLoan: loan *must* have a non-null id parameter!");
	}
	Loan loanToDelete = this.entityManager.find(Loan.class,loan.getId());
	if (loanToDelete == null) {
	    throw new IllegalArgumentException("deleteLoan: loan with given id *doesn't* exist in database!");
	}
	this.entityManager.remove(loanToDelete);
    }

    @Override
    public Loan findLoanById(Long id) {
	if (id == null) {
	    throw new IllegalArgumentException("findLoanById: parameter id can't be null!");
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
    public Collection<Loan> findLoansByCustomer(Customer customer) {
	if (customer == null) {
	    throw new IllegalArgumentException("findLoansByCustomer: parameter customer can't be null!");
	}
	if (customer.getId() == null) {
	    throw new IllegalArgumentException("findLoansByCustomer: customer's id can't be null!");
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
