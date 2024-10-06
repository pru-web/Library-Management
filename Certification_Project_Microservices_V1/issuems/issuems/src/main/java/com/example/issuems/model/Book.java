package com.example.issuems.model;

public class Book {

     Integer id;
     Character isbn;
     Integer totalCopies;
     Integer issuedCopies;

    public Integer getId() {
        return id;
    }

    public Character getIsbn() {
        return isbn;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public Integer getIssuedCopies() {
        return issuedCopies;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIsbn(Character isbn) {
        this.isbn = isbn;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setIssuedCopies(Integer issuedCopies) {
        this.issuedCopies = issuedCopies;
    }

}
