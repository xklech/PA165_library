package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Customer;
import cz.muni.fi.pa165.library.entity.Loan;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * Implementation of CustomerDao interface.
 *
 * @author Michal Sukupčák
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void addCustomer(Customer customer) {
	if (customer == null) {
	    throw new IllegalArgumentException("addCustomer: customer parameter can't be null!");
	}
	if (customer.getId() != null) {
	    throw new IllegalArgumentException("addCustomer: customer *can't* have a non-null id parameter!");
	}
	this.entityManager.persist(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
	if (customer == null) {
	    throw new IllegalArgumentException("updateCustomer: customer parameter can't be null!");
	}
	if (customer.getId() == null) {
	    throw new IllegalArgumentException("updateCustomer: customer *must* have a non-null id parameter!");
	}
	Customer customerToUpdate = this.entityManager.find(Customer.class,customer.getId());
	if (customerToUpdate == null) {
	    throw new IllegalArgumentException("updateCustomer: customer with given id *doesn't* exist in database!");
	}
	customerToUpdate.setFirstName(customer.getFirstName());
	customerToUpdate.setLastName(customer.getLastName());
	customerToUpdate.setAddress(customer.getAddress());
	customerToUpdate.setDateOfBirth(customer.getDateOfBirth());
	customerToUpdate.setPid(customer.getPid());
    }

    @Override
    public void deleteCustomer(Customer customer) {
	if (customer == null) {
	    throw new IllegalArgumentException("deleteCustomer: parameter customer can't be null!");
	}
	if (customer.getId() == null) {
	    throw new IllegalArgumentException("deleteCustomer: customer *must* have a non-null id parameter!");
	}
	Customer customerToDelete = this.entityManager.find(Customer.class,customer.getId());
	if (customerToDelete == null) {
	    throw new IllegalArgumentException("deleteCustomer: customer with given id *doesn't* exist in database!");
	}
	this.entityManager.remove(customerToDelete);
    }

    @Override
    public Customer findCustomerById(Long id) {
	if (id == null) {
	    throw new IllegalArgumentException("findCustomerById: parameter id can't be null!");
	}
	return this.entityManager.find(Customer.class,id);
    }

    @Override
    public Collection<Customer> findAllCustomers() {
	TypedQuery q = this.entityManager.createQuery("SELECT c FROM Customer c",Customer.class);
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomersByBook(Book book) {
	if (book == null) {
	    throw new IllegalArgumentException("findCustomersByBook: parameter book can't be null!");
	}
	if (book.getId() == null) {
	    throw new IllegalArgumentException("findCustomersByBook: book's id can't be null!");
	}
	TypedQuery q = this.entityManager.createQuery("SELECT c FROM Loan l LEFT JOIN l.customer c LEFT JOIN l.impression i LEFT JOIN i.book b  WHERE b.id = :id",Customer.class);
	q.setParameter("id",book.getId());
	return q.getResultList();
    }

    @Override
    public Customer findCustomerByLoan(Loan loan) {
	if (loan == null) {
	    throw new IllegalArgumentException("findCustomerByLoan: parameter loan can't be null!");
	}
	if (loan.getId() == null) {
	    throw new IllegalArgumentException("findCustomerByLoan: loan's id can't be null!");
	}
	TypedQuery q = this.entityManager.createQuery("SELECT c FROM Loan l LEFT JOIN l.customer c WHERE l.id = :id",Customer.class);
	q.setParameter("id",loan.getId());
	return (Customer) q.getSingleResult();
    }

    @Override
    public Collection<Customer> findCustomerByName(String firstName, String lastName) {
	if (firstName == null && lastName == null) {
	    throw new IllegalArgumentException("firstName and lastName: parameter firstName and lastName can't be both null!");
	}
	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
	CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
	Root r = cq.from(Customer.class);
        if (firstName != null && lastName != null) {
            cq.where(cb.like(r.get("firstName"),firstName),cb.like(r.get("lastName"),lastName));
        } else if(firstName == null) {
            cq.where(cb.like(r.get("lastName"),lastName));
        } else {
            cq.where(cb.like(r.get("firstName"),firstName));
        }
	TypedQuery q = this.entityManager.createQuery(cq);
	return q.getResultList();
    }
    
}
