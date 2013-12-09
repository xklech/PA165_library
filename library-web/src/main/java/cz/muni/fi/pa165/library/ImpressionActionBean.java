/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library;

import static cz.muni.fi.pa165.library.BooksActionBean.log;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.service.ImpressionService;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import java.util.Arrays;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jarek
 */
@UrlBinding("/impressions/{$event}/{book.id}/{impression.id}")
public class ImpressionActionBean extends BaseActionBean {

    final static Logger log = LoggerFactory.getLogger(ImpressionActionBean.class);

    @SpringBean
    protected BookService bookService;

    @SpringBean
    protected ImpressionService impressionService;

    private BookTo book;

    @ValidateNestedProperties(value = {
	@Validate(on = {"add"}, field = "initialDamage", required = true),
	@Validate(on = {"add"}, field = "damage", required = true),
	@Validate(on = {"add"}, field = "status", required = true),})
    private ImpressionTo impression;

    private List<ImpressionTo> impressions;

    @DefaultHandler
    public Resolution list() {
	if (book == null || book.getId() == null || (book = bookService.findBookById(book.getId())) == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", book.getId()));
	    return new RedirectResolution(BooksActionBean.class);
	}
	try {
	    impressions = impressionService.findImpressionsByBook(book);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	return new ForwardResolution("/impression/list.jsp");
    }

    public Resolution add() {
	if (book == null || book.getId() == null || (book = bookService.findBookById(book.getId())) == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", book.getId()));
	    return new RedirectResolution(BooksActionBean.class);
	}
	try {
	    impression.setBookTo(book);
	    impressionService.addImpression(impression);
	} catch (Exception ex) {
	    log.debug("add impression exception: {}", ex);
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("impression.add.error", book.getId()));
	    return new RedirectResolution(this.getClass()).addParameter("book.id", book.getId());
	}
	return new RedirectResolution(this.getClass()).addParameter("book.id", book.getId());
    }

    public Resolution delete() {
	if (book == null || book.getId() == null || (book = bookService.findBookById(book.getId())) == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", book.getId()));
	    return new RedirectResolution(BooksActionBean.class);
	}
	if (impression == null || impression.getId() == null || (impression = impressionService.findImpressionById(impression.getId())) == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("impression.list.missing", (impression == null ? '?' : impression.getId())));
	    return new RedirectResolution(this.getClass()).addParameter("book.id", book.getId());
	}
	try {
	    impressionService.deleteImpression(impression);
	} catch (Exception ex) {
	    log.debug("remove impression exception: {}", ex);
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("impression.delete.error", impression.getId()));
	}
	return new RedirectResolution(this.getClass()).addParameter("book.id", book.getId());
    }

    public Resolution edit() {
	if (book == null || book.getId() == null || (book = bookService.findBookById(book.getId())) == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("books.list.missing", book.getId()));
	    return new RedirectResolution(BooksActionBean.class);
	}
	if (impression == null || impression.getId() == null || (impression = impressionService.findImpressionById(impression.getId())) == null) {
	    getContext().getValidationErrors().addGlobalError(new LocalizableError("impression.list.missing", (impression == null ? '?' : impression.getId())));
	    return new RedirectResolution(this.getClass()).addParameter("book.id", book.getId());
	}
	return new ForwardResolution("/impression/edit.jsp");
    }

    public Resolution save() {
	log.debug("save() book={} impression={}", book, impression);
	impressionService.updateImpression(impression);
	getContext().getMessages().add(new LocalizableMessage("impression.save.message", impression.getId(), escapeHTML(book.getName()), escapeHTML(book.getAuthor())));
	return new RedirectResolution(this.getClass()).addParameter("book.id", book.getId());
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"list", "delete", "edit", "save"})
    public void loadBookFromDatabase() {
	log.debug("impression before list,delete, edit");
	String bookIdStr = getContext().getRequest().getParameter("book.id");
	if (bookIdStr == null) {
	    return;
	}
	Long id;
	try {
	    id = Long.parseLong(bookIdStr);
	} catch (NumberFormatException ex) {
	    log.debug("loadBookFromDatabase() number format exception - input:{}", bookIdStr);
	    return;
	}
	try {
	    book = bookService.findBookById(id);
	} catch (Exception ex) {
	    /** @todo response to IllegalArgumentException */
	}
	if ("edit".equals(getContext().getEventName()) || "save".equals(getContext().getEventName())) {
	    String impressionIdStr = getContext().getRequest().getParameter("impression.id");
	    if (impressionIdStr == null) {
		return;
	    }
	    try {
		impression = impressionService.findImpressionById(Long.parseLong(impressionIdStr));
	    } catch (NumberFormatException ex) {
		log.debug("loadBookFromDatabase() number format exception - input:{}", bookIdStr);
	    }
	}
    }

    public BookTo getBook() {
	return book;
    }

    public void setBook(BookTo book) {
	this.book = book;
    }

    public ImpressionTo getImpression() {
	return impression;
    }

    public void setImpression(ImpressionTo impression) {
	this.impression = impression;
    }

    public List<ImpressionTo> getImpressions() {
	return impressions;
    }

    public void setImpressions(List<ImpressionTo> impressions) {
	this.impressions = impressions;
    }

}
