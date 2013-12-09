package cz.muni.fi.pa165.library.to;

import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Transfer object for entity Impression
 *
 * @author Petr Vacek
 */

public class ImpressionTo implements Serializable {
    
    private Long id;

    private BookTo bookTo;
    
    private DamageType initialDamage;
    
    private DamageType damage;
    
    private StatusType status;

    public ImpressionTo(Long id, BookTo bookTo, DamageType initialDamage, DamageType damage, StatusType status) {
        this.id = id;
        this.bookTo = bookTo;
        this.initialDamage = initialDamage;
        this.damage = damage;
        this.status = status;
    }

    public ImpressionTo() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookTo getBookTo() {
        return bookTo;
    }

    public void setBookTo(BookTo bookTo) {
        this.bookTo = bookTo;
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
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ImpressionTo other = (ImpressionTo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ImpressionTO{" + "id=" + id + ", bookTo=" + bookTo + ", initialDamage=" + initialDamage + ", damage=" + damage + ", status=" + status + '}';
    }
    
}
