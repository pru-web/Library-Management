package com.example.bookms.domain;

import javax.persistence.*;

// isbn, title, publishedDate, totalCopies, issuedCopies, author
@Entity
@Table(name = "books_Data")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Character isbn;

    public String title;

    public String author;

    public Integer date;

    public Integer totalCopies;

    public Integer issuedCopies;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIsbn(Character isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setIssuedCopies(Integer issuedCopies) {
        this.issuedCopies = issuedCopies;
    }

    public Integer getId() {
        return id;
    }

    public Character getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getDate() {
        return date;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public Integer getIssuedCopies() {
        return issuedCopies;
    }

}
