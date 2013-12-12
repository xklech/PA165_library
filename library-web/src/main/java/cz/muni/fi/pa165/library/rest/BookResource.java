package cz.muni.fi.pa165.library.rest;

import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Mask
 */
@XmlRootElement
public class BookResource {
    
    private Long id;

    private String name;
    
    private String isbn;

    private String department;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date publishDate;

    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPlain() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "BookResource{id=" + id + ", "
                + "name=" + name + ", "
                + "author=" + author + ", "
                + "department=" + department + ", "
                + "isbn=" + isbn + ", "
                + "publishDate=" + publishDate.toString() + '}';
    }
}
