package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.to.BookTo;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
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
public class BooksActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(BooksActionBean.class);

    @SpringBean
    protected BookService bookService;

    @ValidateNestedProperties(value = {
	@Validate(on = {"add", "save"}, field = "author", required = true),
	@Validate(on = {"add", "save"}, field = "name", required = true),
	@Validate(on = {"add", "save"}, field = "isbn", required = true, minlength = 12),
	@Validate(on = {"add", "save"}, field = "publishDate", required = true),
	@Validate(on = {"add", "save"}, field = "department", required = true)
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

    @Validate(on = {"findById"}, required = true, minvalue = 0)
    private Long findId;
    @Validate(on = {"findByName"}, required = true, minlength = 3)
    private String findName;
    @Validate(on = {"findByAuthor"}, required = true, minlength = 3)
    private String findAuthor;
    @Validate(on = {"findByDepartment"}, required = true, minlength = 3)
    private String findDepartment;

    @Validate(on = {"findByDate"})
    private Date findDateFrom;
    @Validate(on = {"findByDate"})
    private Date findDateTo;

    private boolean validationError;

    public Resolution findById() {
	log.debug("findById() ");
	BookTo bookTo = null;
	try {
	    bookTo = bookService.findBookById(findId);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	if (bookTo != null) {
	    books = Arrays.asList(bookTo);
	} else {
	    getContext().getMessages().add(new LocalizableMessage("book.findId.message", findId));
	    return getContext().getSourcePageResolution();
	}
	return new ForwardResolution("/book/list.jsp");
    }

    public Resolution findByName() {
	log.debug("findByName() ");
	try {
	    books = bookService.findBooksByName(findName);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	if (books == null || books.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("book.findName.message", findName));
	    return getContext().getSourcePageResolution();
	}
	return new ForwardResolution("/book/list.jsp");
    }

    public Resolution findByAuthor() {
	log.debug("findByAuthor() ");
	try {
	    books = bookService.findBooksByAuthor(findAuthor);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	if (books == null || books.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("book.findAuthor.message", findAuthor));
	    return getContext().getSourcePageResolution();
	}
	return new ForwardResolution("/book/list.jsp");
    }

    public Resolution findByDepartment() {
	log.debug("findByDepartment() ");
	try {
	    books = bookService.findBooksByDepartment(findDepartment);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	if (books == null || books.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("book.findDepartment.message", findDepartment));
	    return getContext().getSourcePageResolution();
	}
	return new ForwardResolution("/book/list.jsp");
    }

    public Resolution findByDate() {
	log.debug("findByDate() ");
	try {
	    books = bookService.findBooksByPublishDate(findDateFrom, findDateTo);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	if (books == null || books.isEmpty()) {
	    DateFormat f = DateFormat.getDateInstance(DateFormat.LONG, getContext().getRequest().getLocale());
	    getContext().getMessages().add(new LocalizableMessage("book.findPublishDate.message", (findDateFrom == null ? "neomezeno" : f.format(findDateFrom)), (findDateTo == null ? "neomezeno" : f.format(findDateTo))));
	    return getContext().getSourcePageResolution();
	}
	return new ForwardResolution("/book/list.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"delete", "edit"})
    public void loadBookFromDatabase() {
	log.debug("before delete, edit");
	String idStr = getContext().getRequest().getParameter("book.id");
	if (idStr == null) {
	    return;
	}
	Long id;
	try {
	    id = Long.parseLong(idStr);
	} catch (NumberFormatException ex) {
	    log.debug("loadBookFromDatabase() number format exception - input:{}", idStr);
	    return;
	}
	try {
	    book = bookService.findBookById(id);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
    }

    public Resolution add() {
	log.debug("add() book={}", book);
	try {
	    bookService.save(book);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	getContext().getMessages().add(new LocalizableMessage("book.add.message", escapeHTML(book.getName()), escapeHTML(book.getAuthor()), book.getId()));
	return new RedirectResolution(this.getClass());
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
	log.debug("errors() {}", errors);
	if ("add".equals(getContext().getEventName())) {
	    log.debug("add() validationError =true ");
	    validationError = true;
	}
	return null;
    }

    @DefaultHandler
    public Resolution defaultHandler() {
	log.debug("default() ", book);
	return new ForwardResolution("/books.jsp");
    }

    public Resolution save() {
	log.debug("save() book={}", book);
	try {
	    bookService.updateBook(book);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	getContext().getMessages().add(new LocalizableMessage("book.save.message", escapeHTML(book.getName()), escapeHTML(book.getAuthor()), book.getId()));
	return new RedirectResolution(this.getClass());
    }

    public Resolution delete() {
	log.debug("delete() book={}", book);
	try {
	    book = bookService.findBookById(book.getId());
	    bookService.deleteBook(book);
	    getContext().getMessages().add(new LocalizableMessage("books.list.deleted", escapeHTML(book.getName())));
	} catch (Exception ex) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", book.getId()));
	}
	return new RedirectResolution(this.getClass());
    }

    public Resolution edit() {
	log.debug("edit book={}", book);
	if (book == null || book.getId() == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", book.getId()));
	    return new RedirectResolution(this.getClass());
	}
	return new ForwardResolution("/book/edit.jsp");
    }

    public Long getFindId() {
	return findId;
    }

    public void setFindId(Long findId) {
	this.findId = findId;
    }

    public String getFindName() {
	return findName;
    }

    public void setFindName(String findName) {
	this.findName = findName;
    }

    public String getFindDepartment() {
	return findDepartment;
    }

    public void setFindDepartment(String findDepartment) {
	this.findDepartment = findDepartment;
    }

    public String getFindAuthor() {
	return findAuthor;
    }

    public void setFindAuthor(String findAuthor) {
	this.findAuthor = findAuthor;
    }

    public Date getFindDateFrom() {
	return findDateFrom;
    }

    public void setFindDateFrom(Date findDateFrom) {
	this.findDateFrom = findDateFrom;
    }

    public Date getFindDateTo() {
	return findDateTo;
    }

    public void setFindDateTo(Date findDateTo) {
	this.findDateTo = findDateTo;
    }

    public boolean isValidationError() {
	return validationError;
    }

    public void setValidationError(boolean validationError) {
	this.validationError = validationError;
    }

}
