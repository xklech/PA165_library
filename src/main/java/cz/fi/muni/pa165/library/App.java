package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.BookDao;
import cz.fi.muni.pa165.library.dao.BookDaoImpl;
import cz.fi.muni.pa165.library.dao.LoanDao;
import cz.fi.muni.pa165.library.dao.LoanDaoImpl;
import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.entity.Loan;
import cz.fi.muni.pa165.library.exceptions.BookDaoException;
import cz.fi.muni.pa165.library.exceptions.LoanDaoException;
import cz.fi.muni.pa165.library.service.BookServiceImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Hello world!
 *
 */

public class App 
{
        
    
    
    public static void main( String[] args ) throws BookDaoException, LoanDaoException
    {
        
        /* emf = Persistence.createEntityManagerFactory("LibraryPU");
        EntityManager manager = emf.createEntityManager();
        Book book = new Book("Android", "111-222-444-555", null, "Zbraně", new Date(), "Jarek");
        BookDao bookDAO = new BookDaoImpl(manager);
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
	
	LoanDao loanDAO = new LoanDaoImpl(manager);
	Loan loan = new Loan();
        manager.getTransaction().begin();
            loanDAO.addLoan(loan);
        manager.getTransaction().commit();
	Collection<Loan> loans = loanDAO.findLoansByFromTo(null,new Date());
	for (Loan l : loans) {
	    System.out.println(l);
	}*/
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookServiceImpl bookService = (BookServiceImpl) ctx.getBean("bookService");
        System.out.println(" service: "+bookService);
        

        Book book = new Book("Java", "123456789", null, "Skola", null, "Pepa");

        bookService.save(book);
        


        System.err.println(bookService.findByName(book.getName()));
    }
}
