package com.example.issuems.domain;
import javax.persistence.*;


@Entity
@Table(name = "Issuer")
public class IssueRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

     Long id;
     Character isbn;
     Integer custId;
     Integer noOfCopies;

    // Constructors, getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getIsbn() {
        return isbn;
    }

    public void setIsbn(Character isbn) {
        this.isbn = isbn;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }
}

