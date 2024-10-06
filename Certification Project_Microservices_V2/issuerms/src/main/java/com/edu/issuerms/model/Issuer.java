package com.edu.issuerms.model;


import javax.persistence.*;

@Entity
@Table(name = "issuer")
public class Issuer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer issuerId;
    Integer isbn;
    Integer custId;
    Integer noOfCopies;

    public Integer getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
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
