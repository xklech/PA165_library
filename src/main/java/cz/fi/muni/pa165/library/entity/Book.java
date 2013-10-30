package cz.fi.muni.pa165.library.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *  Book entity holding information abou one book.
 * 
 * @author Jaroslav Klech
 */
@Entity
public class Book implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length=50)
    private String name;
    
    @Column(length=20)
    private String isbn;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Impression> impressions;
    
    @Column(length=50)
    private String department;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date publishDate;
    
    @Column(length=100)
    private String author;

    public Book() {
    }

    public Book(String name, String isbn, List<Impression> impressions, String department, Date publishDate, String author) {
        this.name = name;
        this.isbn = isbn;
        this.impressions = impressions;
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

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public List<Impression> getImpressions() {
        return impressions;
    }

    public void setImpressions(List<Impression> impressions) {
        this.impressions = impressions;
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
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Book other = (Book) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name=" + name + ", ISBN=" + isbn + ", impressions=" + ", department=" + department + ", publishDate=" + publishDate + ", author=" + author + '}';
    }


}
