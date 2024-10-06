package com.example.issuems.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//import java.awt.print.Book;
import com.example.issuems.model.Book;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//base URL path for all endpoints defined in this controller
@RequestMapping("/issue")
public class IssuemsResource {
    //used to make HTTP requests to bookms service
    @Autowired
    private WebClient webClient;
    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;
    private IssueRepository issueRepository;

    //?this api not working?
    //get books from bookms
    @GetMapping("/get-book")
    //This method retrieves a list of books from the bookms service,
    // filters the books based on availability,
    // and maps them to a list of BookInfo objects.
    public Mono<List<BookInfo>> getAllBooksFromBookms() {

        //send a GET request to the "/books" endpoint of the bookms service.

        return webClient.get().uri("/books")
                .retrieve()
                //converts the books details in a list and keeps them in the class "Book"
                .bodyToMono(new ParameterizedTypeReference<List<Book>>() {})
                //maps the list of retrieved books, filters them based on availability,
                // and converts them to a list of BookInfo objects using the convertToBookInfo method.
                .map(books -> books.stream()
                        .filter(book -> book.getTotalCopies() - book.getIssuedCopies() > 0)
                        .map(this::convertToBookInfo)
                        .collect(Collectors.toList())
                );
        }
    //method, helps converts Book object to BookInfo object
    // extracts relevant fields from the Book object and sets them in a new BookInfo instance.
    private BookInfo convertToBookInfo(Book book) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(book.getId());
        bookInfo.setTotalCopies(book.getTotalCopies());
        bookInfo.setIssuedCopies(book.getIssuedCopies());
        bookInfo.setIsbn(book.getIsbn());
        return bookInfo;
    }

    //endpoint to issue book to cust, IssueRequest contains details of book wanted
    //also checks availability of book
    @PostMapping("/issue-book")
    public ResponseEntity<String> issueBook(@RequestBody IssueRequest issueRequest) {
        Character isbn = issueRequest.getIsbn();
        Integer custId = issueRequest.getCustId();
        Integer noOfCopies = issueRequest.getNoOfCopies();

        Mono<ResponseEntity<List<BookInfo>>> responseEntityMono = webClient.get().uri("/books").retrieve().toEntity(
                new ParameterizedTypeReference<List<BookInfo>>() {
                }
        );

        List<BookInfo> bookInfoList = responseEntityMono.block().getBody();
        //retrieving book from Isbn, checks if that book is available
        BookInfo requestedBook = bookInfoList.stream()
                .filter(bookInfo -> bookInfo.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        if (requestedBook != null && (requestedBook.getTotalCopies() - requestedBook.getIssuedCopies()) >= noOfCopies) {
            IssueRecord issueRecord = new IssueRecord();
            issueRecord.setIsbn(isbn);
            issueRecord.setCustId(custId);
            issueRecord.setNoOfCopies(noOfCopies);
            // if the books is available and issued,Save it in issue record
            issueRepository.save(issueRecord);

            // Update issuedCopies in bookms (using webClient) after a book is issued
            BookInfo updatedBookInfo = new BookInfo();
            updatedBookInfo.setId(requestedBook.getId());
            updatedBookInfo.setTotalCopies(requestedBook.getTotalCopies());
            updatedBookInfo.setIssuedCopies(requestedBook.getIssuedCopies() + noOfCopies);
            updatedBookInfo.setIsbn(requestedBook.getIsbn());

            webClient.put().uri("/books/" + requestedBook.getId())
                    .body(Mono.just(updatedBookInfo), BookInfo.class)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();


            return ResponseEntity.ok("Book issued successfully");
        } else {
            return ResponseEntity.badRequest().body("Book not available for issuing");
        }
    }


    // Define a class to hold the book information
    private static class BookInfo {
        private Integer id;
        private Integer totalCopies;
        private Integer issuedCopies;
        private Character isbn;

        // Getters and setters...
        public void setId(Integer id) {
            this.id = id;
        }

        public void setTotalCopies(Integer totalCopies) {
            this.totalCopies = totalCopies;
        }

        public void setIssuedCopies(Integer issuedCopies) {
            this.issuedCopies = issuedCopies;
        }

        public void setIsbn(Character isbn) {
            this.isbn = isbn;
        }


        public Integer getId() {
            return id;
        }

        public Integer getTotalCopies() {
            return totalCopies;
        }

        public Integer getIssuedCopies() {
            return issuedCopies;
        }

        public Character getIsbn() {
            return isbn;
        }

    }

    // Define a class to hold the issue request parameters
    private static class IssueRequest {
        private Character isbn;
        private Integer custId;
        private Integer noOfCopies;

        // Getters and setters...
        public void setIsbn(Character isbn) {
            this.isbn = isbn;
        }

        public void setCustId(Integer custId) {
            this.custId = custId;
        }

        public void setNoOfCopies(Integer noOfCopies) {
            this.noOfCopies = noOfCopies;
        }

        public Character getIsbn() {
            return isbn;
        }

        public Integer getCustId() {
            return custId;
        }

        public Integer getNoOfCopies() {
            return noOfCopies;
        }
    }

    // Define an IssueRepository interface to manage issue records
    // This depends on the database setup
    private interface IssueRepository {
        void save(IssueRecord issueRecord);
    }

    // Define a class to represent an issue record
    private static class IssueRecord {
        private Character isbn;
        private Integer custId;
        private Integer noOfCopies;

        // Getters and setters...

        public Character getIsbn() {
            return isbn;
        }

        public Integer getCustId() {
            return custId;
        }

        public Integer getNoOfCopies() {
            return noOfCopies;
        }

        public void setIsbn(Character isbn) {
            this.isbn = isbn;
        }

        public void setCustId(Integer custId) {
            this.custId = custId;
        }

        public void setNoOfCopies(Integer noOfCopies) {
            this.noOfCopies = noOfCopies;
        }

    }

    @GetMapping("/hello-client")
    public String getHelloFromClient() {
        return "Hello Client";
    }
}

