/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.CustomerService;
import cz.muni.fi.pa165.library.service.ImpressionService;
import cz.muni.fi.pa165.library.service.LoanService;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.CustomerTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.to.LoanTo;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michal Sukupčák
 */
@UrlBinding("/loans/{$event}/{$loan.id}")
public class LoansActionBean extends BaseActionBean implements ValidationErrorHandler {

    /* ---------------------------------------------------------------------- */
    /* Logger */
    
    final static Logger log = LoggerFactory.getLogger(BooksActionBean.class);   
    
    /* ---------------------------------------------------------------------- */
    /* Spring beans */
    
    @SpringBean
    protected LoanService loanService;
    
    @SpringBean
    protected CustomerService customerService;
    
    @SpringBean
    protected ImpressionService impressionService;
    
    @SpringBean
    protected BookService bookService;
    
    /* ---------------------------------------------------------------------- */
    /* Validation errors */
    
    private Boolean validationError;
    
    public Boolean isValidationError() {
	return this.validationError;
    }
    
    public void setValidationError(boolean validationError) {
        this.validationError = validationError;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Loan */
    
    @ValidateNestedProperties(value = {
	@Validate(on = {"add","save"}, field = "customer", required = true),
	@Validate(on = {"add","save"}, field = "impression", required = true),
	@Validate(on = {"add","save"}, field = "fromDate", required = true),
	@Validate(on = {"add","save"}, field = "toDate", required = true)
    })
    private LoanTo loan;
    
    public LoanTo getLoan() {
	return this.loan;
    }
    
    public void setLoan(LoanTo loan) {
	this.loan = loan;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Loan list */
    
    private Collection<LoanTo> loans;
    
    public Collection<LoanTo> getLoans() {
	return this.loans;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Customer list */
    
    private Collection<CustomerTo> customers;
    
    public Collection<CustomerTo> getCustomers() {
	return this.customers;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Book list */
    
    private List<BookTo> books;
    
    public List<BookTo> getBooks() {
	return this.books;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Impression list */
    
    private Collection<ImpressionTo> impressions;
    
    public Collection<ImpressionTo> getImpressions() {
	return this.impressions;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Find loan by id */
    
    @Validate(on = {"findById"}, required = true, minvalue = 0)
    private Long findId;
    
    public Long getFindById() {
	return this.findId;
    }
    
    public void setFindById(Long findId) {
	this.findId = findId;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Find loans by customer */
    
    @Validate(on = {"findByCustomer"}, required = true, minvalue = 0)
    private Long findCustomer;
    
    public Long getFindByCustomer() {
	return this.findCustomer;
    }

    public void setFindByCustomer(Long findCustomer) {
	this.findCustomer = findCustomer;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Find loans by from date */
    
    @Validate(on = {"findByFromTo"})
    private Date findFrom;
    
    public Date getFindByFrom() {
	return this.findFrom;
    }

    public void setFindByFrom(Date findFrom) {
	this.findFrom = findFrom;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Find loans by to date */
    
    @Validate(on = {"findByFromTo"})
    private Date findTo;
    
    public Date getFindByTo() {
	return this.findTo;
    }

    public void setFindByTo(Date findTo) {
	this.findTo = findTo;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Error handler */
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        log.debug("errors() {}",errors);
        if ("add".equals(getContext().getEventName())){
	    log.debug("add() validationError =true ");
            this.validationError = true;
        }
        return null;
    }
    
    /* ---------------------------------------------------------------------- */
    /* Action handlers */
    
    @DefaultHandler
    public Resolution defaultHandler() {
	log.debug("defaultHandler()");
	this.init();
        return new ForwardResolution("/loans.jsp");
    }
    
    public Resolution findById() {
	log.debug("findById()");
	this.init();
	this.loans = Arrays.asList(this.loanService.findLoanById(this.findId));
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findById.message",this.findId));  
	    return getContext().getSourcePageResolution();
	} else {
	    return new RedirectResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findAllActive() {
	log.debug("findAllActive()",this.loans);
	this.init();
	this.loans = this.loanService.findAllActiveLoans();
	if (this.loans.isEmpty()) {
	    getContext().getMessages().add(new LocalizableMessage("loans.findAllActive.message",this.findId));  
	    return getContext().getSourcePageResolution();
	} else {
	    return new RedirectResolution("/loan/list.jsp");
	}
    }
    
    public Resolution findByCustomer() {
	this.init();
	throw new UnsupportedOperationException("findByCustomer");
    }
    
    public Resolution findByFromTo() {
	this.init();
	throw new UnsupportedOperationException("findByFromTo");
    }
 
    public Resolution prepare() {
	throw new UnsupportedOperationException("add");
    }
    
    public Resolution add() {
	throw new UnsupportedOperationException("add");
    }
    
    public Resolution save() {
	throw new UnsupportedOperationException("save");
    }
    
    public Resolution edit() {
	throw new UnsupportedOperationException("edit");
    }
    
    public Resolution delete() {
	throw new UnsupportedOperationException("delete");
    }
    
    /* ---------------------------------------------------------------------- */
 
    private void init() {
	this.customerService.addCustomer(new CustomerTo(null,"John","Bon Jovi","ffdp",new Date(),"123456789"));
	this.customers = this.customerService.findAllCustomers();
	this.impressions = null;
	this.books = this.bookService.findAllBooks();
    }
    
}
