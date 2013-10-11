package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Customer;
import cz.fi.muni.pa165.library.entity.Customer_;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.exceptions.CustomerDAOException;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of CustomerDAO interface.
 *
 * @author Michal Sukupčák
 */
public class CustomerDAOImpl implements CustomerDAO {

    private EntityManager em;
    
    public CustomerDAOImpl(EntityManager em) {
	this.em = em;
    }
    
    @Override
    public void addCustomer(Customer customer) throws CustomerDAOException {
	if (customer == null) {
	    throw new CustomerDAOException("addCustomer: customer parameter can't be null!");
	}
	if (customer.getId() != null) {
	    throw new CustomerDAOException("addCustomer: customer *can't* have a non-null id parameter!");
	}
	this.em.persist(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerDAOException {
	if (customer == null) {
	    throw new CustomerDAOException("updateCustomer: customer parameter can't be null!");
	}
	if (customer.getId() == null) {
	    throw new CustomerDAOException("updateCustomer: customer *must* have a non-null id parameter!");
	}
	Customer customerToUpdate = this.em.find(Customer.class,customer.getId());
	if (customerToUpdate == null) {
	    throw new CustomerDAOException("updateCustomer: customer with given id *doesn't* exist in database!");
	}
	customerToUpdate.setFirstName(customer.getFirstName());
	customerToUpdate.setLastName(customer.getLastName());
	customerToUpdate.setAddress(customer.getAddress());
	customerToUpdate.setDateOfBirth(customer.getDateOfBirth());
	customerToUpdate.setPid(customer.getPid());
	customerToUpdate.setLoans(customer.getLoans());
    }

    @Override
    public void deleteCustomer(Customer customer) throws CustomerDAOException {
	if (customer == null) {
	    throw new CustomerDAOException("deleteCustomer: parameter customer can't be null!");
	}
	if (customer.getId() == null) {
	    throw new CustomerDAOException("deleteCustomer: customer *must* have a non-null id parameter!");
	}
	Customer customerToDelete = this.em.find(Customer.class,customer.getId());
	if (customerToDelete == null) {
	    throw new CustomerDAOException("deleteCustomer: customer with given id *doesn't* exist in database!");
	}
	this.em.remove(customerToDelete);
    }

    @Override
    public Customer findCustomerById(Long id) throws CustomerDAOException {
	if (id == null) {
	    throw new CustomerDAOException("findCustomerById: parameter id can't be null!");
	}
	return this.em.find(Customer.class,id);
    }

    @Override
    public Collection<Customer> findAllCustomers() {
	TypedQuery q = this.em.createQuery("SELECT c FROM Customer c",Customer.class);
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomersByBook(Book book) throws CustomerDAOException {
	if (book == null) {
	    throw new CustomerDAOException("findCustomersByBook: parameter book can't be null!");
	}
	if (book.getId() == null) {
	    throw new CustomerDAOException("findCustomersByBook: book's id can't be null!");
	}
	TypedQuery q = this.em.createQuery("SELECT c FROM Customer c LEFT JOIN c.book b WHERE b.id = :id",Customer.class);
	q.setParameter("id",book.getId());
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomerByLoan(Loan loan) throws CustomerDAOException {
	if (loan == null) {
	    throw new CustomerDAOException("findCustomerByLoan: parameter loan can't be null!");
	}
	if (loan.getId() == null) {
	    throw new CustomerDAOException("findCustomerByLoan: loan's id can't be null!");
	}
	TypedQuery q = this.em.createQuery("SELECT c FROM Customer c LEFT JOIN c.loan l WHERE l.id = :id",Customer.class);
	q.setParameter("id",loan.getId());
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomerByName(String firstName, String lastName) throws CustomerDAOException {
	if (firstName == null) {
	    throw new CustomerDAOException("firstName: parameter firstName can't be null!");
	}
	if (lastName == null) {
	    throw new CustomerDAOException("lastName: parameter lastName can't be null!");
	}
	CriteriaBuilder cb = this.em.getCriteriaBuilder();
	CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
	Root r = cq.from(Customer.class);
	cq.where(cb.like(r.get(Customer_.firstName),firstName),cb.like(r.get(Customer_.lastName),lastName));
	TypedQuery q = this.em.createQuery(cq);
	return q.getResultList();
    }
    
}
