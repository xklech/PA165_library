package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Customer;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.exceptions.CustomerDaoException;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of CustomerDao interface.
 *
 * @author Michal Sukupčák
 */
public class CustomerDaoImpl implements CustomerDao {

    private EntityManager em;
    
    public CustomerDaoImpl(EntityManager em) {
	this.em = em;
    }
    
    @Override
    public void addCustomer(Customer customer) throws CustomerDaoException {
	if (customer == null) {
	    throw new CustomerDaoException("addCustomer: customer parameter can't be null!");
	}
	if (customer.getId() != null) {
	    throw new CustomerDaoException("addCustomer: customer *can't* have a non-null id parameter!");
	}
	this.em.persist(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerDaoException {
	if (customer == null) {
	    throw new CustomerDaoException("updateCustomer: customer parameter can't be null!");
	}
	if (customer.getId() == null) {
	    throw new CustomerDaoException("updateCustomer: customer *must* have a non-null id parameter!");
	}
	Customer customerToUpdate = this.em.find(Customer.class,customer.getId());
	if (customerToUpdate == null) {
	    throw new CustomerDaoException("updateCustomer: customer with given id *doesn't* exist in database!");
	}
	customerToUpdate.setFirstName(customer.getFirstName());
	customerToUpdate.setLastName(customer.getLastName());
	customerToUpdate.setAddress(customer.getAddress());
	customerToUpdate.setDateOfBirth(customer.getDateOfBirth());
	customerToUpdate.setPid(customer.getPid());
	customerToUpdate.setLoans(customer.getLoans());
    }

    @Override
    public void deleteCustomer(Customer customer) throws CustomerDaoException {
	if (customer == null) {
	    throw new CustomerDaoException("deleteCustomer: parameter customer can't be null!");
	}
	if (customer.getId() == null) {
	    throw new CustomerDaoException("deleteCustomer: customer *must* have a non-null id parameter!");
	}
	Customer customerToDelete = this.em.find(Customer.class,customer.getId());
	if (customerToDelete == null) {
	    throw new CustomerDaoException("deleteCustomer: customer with given id *doesn't* exist in database!");
	}
	this.em.remove(customerToDelete);
    }

    @Override
    public Customer findCustomerById(Long id) throws CustomerDaoException {
	if (id == null) {
	    throw new CustomerDaoException("findCustomerById: parameter id can't be null!");
	}
	return this.em.find(Customer.class,id);
    }

    @Override
    public Collection<Customer> findAllCustomers() {
	TypedQuery q = this.em.createQuery("SELECT c FROM Customer c",Customer.class);
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomersByBook(Book book) throws CustomerDaoException {
	if (book == null) {
	    throw new CustomerDaoException("findCustomersByBook: parameter book can't be null!");
	}
	if (book.getId() == null) {
	    throw new CustomerDaoException("findCustomersByBook: book's id can't be null!");
	}
	TypedQuery q = this.em.createQuery("SELECT c FROM Customer c LEFT JOIN c.book b WHERE b.id = :id",Customer.class);
	q.setParameter("id",book.getId());
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomerByLoan(Loan loan) throws CustomerDaoException {
	if (loan == null) {
	    throw new CustomerDaoException("findCustomerByLoan: parameter loan can't be null!");
	}
	if (loan.getId() == null) {
	    throw new CustomerDaoException("findCustomerByLoan: loan's id can't be null!");
	}
	TypedQuery q = this.em.createQuery("SELECT c FROM Customer c LEFT JOIN c.loan l WHERE l.id = :id",Customer.class);
	q.setParameter("id",loan.getId());
	return q.getResultList();
    }

    @Override
    public Collection<Customer> findCustomerByName(String firstName, String lastName) throws CustomerDaoException {
	if (firstName == null) {
	    throw new CustomerDaoException("firstName: parameter firstName can't be null!");
	}
	if (lastName == null) {
	    throw new CustomerDaoException("lastName: parameter lastName can't be null!");
	}
	CriteriaBuilder cb = this.em.getCriteriaBuilder();
	CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
	Root r = cq.from(Customer.class);
	cq.where(cb.like(r.get("firstName"),firstName),cb.like(r.get("lastName"),lastName));
	TypedQuery q = this.em.createQuery(cq);
	return q.getResultList();
    }
    
}
