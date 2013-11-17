package cz.muni.fi.pa165.library.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Ivana Haraslínová
 */
@Entity
public class Customer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=30)
    private String firstName;
    
    @Column(length=50)
    private String lastName;
    
    @Column(length=75)
    private String address;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    
    @Column(length=11)
    private String pid;

    
    public Customer() {}

    public Customer(Long id, String firstName, String lastName, String address, Date dateOfBirth, String pid) {
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.dateOfBirth = dateOfBirth;
	this.pid = pid;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    public String getPid() {
	return pid;
    }

    public void setPid(String pid) {
	this.pid = pid;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 79 * hash + Objects.hashCode(this.id);
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
	final Customer other = (Customer) obj;
	if (!Objects.equals(this.id, other.id)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Customer{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", pid=" + pid  + '}';
    }

}
