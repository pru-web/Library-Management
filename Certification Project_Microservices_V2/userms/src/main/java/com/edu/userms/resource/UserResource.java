package com.edu.userms.resource;

import com.edu.userms.model.User;
import com.edu.userms.repo.UserRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    private UserRepo repo;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World!";
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersList() {
        System.out.println("**********************");
        System.out.println("**********************");
        System.out.println("**********************");
        return repo.findAll();
    }

    @GetMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getSingleUsers(@PathVariable Integer id) {
        Optional<User> userFound = repo.findById(id);
        if(userFound.isPresent()) {
            return ResponseEntity.ok(userFound.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) throws URISyntaxException {
        User userSaved = repo.save(user);

        return ResponseEntity.created(new URI(userSaved.getId().toString())).body(userSaved);
    }

    @GetMapping("/users-orders")
    @HystrixCommand(fallbackMethod = "ordermsFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public String getOrders() {
        LOGGER.info("About to call orderms");
        return restTemplate.getForObject("http://orderms/orders", String.class);
    }

    private String ordermsFallback() {
        LOGGER.info("Falbback of orderms");
        return "Fallback Response";
    }
}
