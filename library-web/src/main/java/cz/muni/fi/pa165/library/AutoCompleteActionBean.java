/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.library;

import cz.muni.fi.pa165.library.service.BookService;
import cz.muni.fi.pa165.library.to.BookTo;
import java.io.StringReader;
import java.util.List;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.json.JSONArray;

/**
 *
 * @author Jarek
 */
@UrlBinding("/autoComplete/{$event}/{term}")
public class AutoCompleteActionBean extends BaseActionBean{
    @SpringBean
    protected BookService bookService;
    
    private String term;

    public Resolution booksByName(){
        JSONArray json = new JSONArray();
        if (term != null) {
            List<BookTo> books = bookService.findBooksByName(term);
            if (books != null) {
                for(BookTo book: books){
                    json.put(book.getName());
                }
            }
        }
        return new StreamingResolution("text", new StringReader(json.toString()));
    }
    
    public Resolution booksByAuthor(){
        JSONArray json = new JSONArray();
        if (term != null) {
            List<BookTo> books = bookService.findBooksByAuthor(term);
            if (books != null) {
                for (BookTo book: books) {
                    json.put(book.getAuthor());
                }
            }
        }
        return new StreamingResolution("text", new StringReader(json.toString()));
    }
    
    public Resolution booksByDepartment(){
        JSONArray json = new JSONArray();
        if (term != null) {
            List<BookTo> books = bookService.findBooksByDepartment(term);
            if (books != null) {
                for (BookTo book: books) {
                    json.put(book.getDepartment());
                }
            }
        }
        return new StreamingResolution("text", new StringReader(json.toString()));
    }
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
        
}
