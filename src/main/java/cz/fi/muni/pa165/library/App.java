package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.BookDAO;
import cz.fi.muni.pa165.library.dao.BookDAOImpl;
import cz.fi.muni.pa165.library.dao.LoanDAO;
import cz.fi.muni.pa165.library.dao.LoanDAOImpl;
import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.exceptions.BookDAOException;
import cz.fi.muni.pa165.library.exceptions.LoanDAOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws BookDAOException, LoanDAOException
    {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");
        EntityManager manager = emf.createEntityManager();
        Book book = new Book("Android", "111-222-444-555", null, "Zbraně", new Date(), "Jarek");
        BookDAO bookDAO = new BookDAOImpl(manager);
        Impression impression1 = new Impression();
        impression1.setBook(book);
        Impression impression2 = new Impression();
        impression2.setBook(book);
        List<Impression> impressions = new ArrayList<>();
        impressions.add(impression1);
        impressions.add(impression2);
        book.setImpressions(impressions);
        manager.getTransaction().begin();
            bookDAO.addBook(book);
        manager.getTransaction().commit();
	
	LoanDAO loanDAO = new LoanDAOImpl(manager);
	Loan loan = new Loan();
        manager.getTransaction().begin();
            loanDAO.addLoan(loan);
        manager.getTransaction().commit();
	Collection<Loan> loans = loanDAO.findLoansByFromTo(null,new Date());
	for (Loan l : loans) {
	    System.out.println(l);
	}
    }
}
