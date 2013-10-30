package cz.muni.fi.pa165.library.entity;

import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Mask
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Impression.findImpressionsByDamage", query = "SELECT i FROM Impression i WHERE i.damage = :damage"),
    @NamedQuery(name = "Impression.findImpressionsByStatus", query = "SELECT i FROM Impression i WHERE i.status = :status"),
    @NamedQuery(name = "Impression.findImpressionsByBook", query = "SELECT i FROM Impression i WHERE i.book = :book"),
})
public class Impression implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Book book;
    
    private DamageType initialDamage;
    private DamageType damage;
    private StatusType status;

    public Impression() {}
    
    public Impression(  Book book,
                        DamageType initialDamage,
                        DamageType damage,
                        StatusType status)
    {
        this.book = book;
        this.initialDamage = initialDamage;
        this.damage = damage;
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public DamageType getInitialDamage() {
        return initialDamage;
    }

    public void setInitialDamage(DamageType initialDamage) {
        this.initialDamage = initialDamage;
    }

    public DamageType getDamage() {
        return damage;
    }

    public void setDamage(DamageType damage) {
        this.damage = damage;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Impression other = (Impression) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Impression{" + "id=" + id + ", book=" + book + ", initialDamage=" + initialDamage + ", damage=" + damage + ", status=" + status + '}';
    }
    
}
