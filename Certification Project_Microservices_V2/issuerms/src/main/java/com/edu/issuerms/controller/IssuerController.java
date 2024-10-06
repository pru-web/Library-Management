package com.edu.issuerms.controller;


import com.edu.issuerms.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class IssuerController {

    @Autowired
    RestTemplate restTemplate;

    //the method issueBookToCustomer will be invoked when a GET request is made to the /issue_book endpoint.
    //JSON format-> specifies the format of the data being sent or received in an HTTP request or response
    @GetMapping(path = "/issue_book", produces = MediaType.APPLICATION_JSON_VALUE)
    //annotation is used to extract query parameters from the request URL. In this case, the method expects an author parameter as part of the request
    public ResponseEntity issueBookToCustomer(@RequestParam String author) {
        //ResponseEntity<List<Book>> responseList = restTemplate.getForEntity("", Book[].class);
        //Increment the issuedCopies property of the Book object by 1,
        // assuming that the Book class has a method getIssuedCopies() to retrieve the number of issued copies and setIssuedCopies() to update it.
        ResponseEntity<Book> bookObject = restTemplate.getForEntity("http://bookms/books?author="+author, Book.class);
        Book book = bookObject.getBody();
        book.setIssuedCopies(book.getIssuedCopies()+1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(book.toString(), headers);
        ResponseEntity<Book> bookUpdated = restTemplate.exchange("http://bookms/books", HttpMethod.PUT, request, Book.class);
        return ResponseEntity.ok(bookUpdated.getBody());
    }




}
