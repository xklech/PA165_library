package cz.muni.fi.pa165.library.to;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Transfer object for entity Book
 *
 * @author Jaroslav Klech
 */
public class BookTo implements Serializable {
    
    private Long id;

    private String name;
    
    private String isbn;

    private String department;

    private Date publishDate;

    private String author;

    public BookTo() {}
    
    public BookTo(String name, String isbn, String department, Date publishDate, String author) {
        this.name = name;
        this.isbn = isbn;
        this.department = department;
        this.publishDate = publishDate;
        this.author = author;
    }
    
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookTo other = (BookTo) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BookTo{" + "id=" + id + ", name=" + name + ", isbn=" + isbn + ", department=" + department + ", publishDate=" + publishDate + ", author=" + author + '}';
    }
    
}
