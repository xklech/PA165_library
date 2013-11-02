package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import cz.muni.fi.pa165.library.exceptions.LoanDaoException;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.BookServiceImpl;
import cz.muni.fi.pa165.library.service.ImpressionService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTO;
import cz.muni.fi.pa165.library.to.LoanTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        /*BookService bookService = (BookService) ctx.getBean("bookService");
        System.out.println(" service: "+bookService);
        

        BookTo bookTo = new BookTo("Java", "123456789", "Skola", null, "Pepa");

        bookService.save(bookTo);*/
        
        /*LoanService loanService = (LoanService) ctx.getBean("loanService");
        LoanTO loanTo= new LoanTO();
        loanService.addLoan(loanTo);
                
        System.err.println(loanService.findLoanById(loanTo.getId()));*/
        ImpressionService impressionService = (ImpressionService) ctx.getBean("impressionService");
        ImpressionTO impression= new ImpressionTO();
        impressionService.addImpression(impression);
                
        System.err.println(impressionService.findImpressionById(impression.getId()));
        
    }
}
