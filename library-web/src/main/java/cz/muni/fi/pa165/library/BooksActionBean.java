package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.to.BookTo;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jaroslav Klech
 */
@UrlBinding("/books/{$event}/{book.id}")
public class BooksActionBean extends BaseActionBean implements ValidationErrorHandler{
    final static Logger log = LoggerFactory.getLogger(BooksActionBean.class);   
        
    @SpringBean
    protected BookService bookService;
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add"}, field = "author", required = true),
            @Validate(on = {"add"}, field = "name", required = true),
            @Validate(on = {"add"}, field = "isbn", required = true)
    })
    private BookTo book;
    
    private List<BookTo> books;
    public BookTo getBook() {
        return book;
    }

    public void setBook(BookTo book) {
        this.book = book;
    }
    public List<BookTo> getBooks() {
        return books;
    }
    public Resolution findId(){
        log.debug("findId() book={}");
        String ids = getContext().getRequest().getParameter("book.id");
        if (ids != null){
        BookTo bookTo = bookService.findBookById(Long.parseLong(ids));
        books = new ArrayList<>();
        if(bookTo != null){
            books.add(bookTo);
        }
                    
        }
        return new ForwardResolution("/books/index.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"add"})
    public void loadBookFromDatabase() {
        log.debug("before add");
    }

    public Resolution add() {
        log.debug("add() book={}", book);
        bookService.save(book);
        getContext().getMessages().add(new LocalizableMessage("book.add.message",escapeHTML(book.getName()),escapeHTML(book.getAuthor())));
        return new RedirectResolution(this.getClass());
    }
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        log.debug("errors() ", errors);
        //return null to let the event handling continue
        return null;
    }
    
    @DefaultHandler
    public Resolution defaultHandler(){
        log.debug("default() ", book);
        return new ForwardResolution("/books.jsp");
    }
    
   public Resolution save() {
        log.debug("save() book={}", book);
        bookService.updateBook(book);
        return new RedirectResolution(this.getClass(), "list");
    }
}
