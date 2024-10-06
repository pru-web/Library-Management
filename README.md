# Library Management

Application developed using Spring Boot with Microservices architecture.

## Prerequisites

- **Java 11**: The application is built using Java 11. You can download it from [Adoptium](https://adoptium.net/).
- **Maven**: For managing dependencies and building the application. Download it from [Maven's official website](https://maven.apache.org/download.cgi).
- **Optional - IDE**: An Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse can be helpful for development.

## Libraries and Frameworks

- **Spring Boot**: Version 2.5.x or higher
- **H2 Database**: For in-memory database management
- **OAuth2**: For securing the application
- **Swagger**: For API documentation
- **Prometheus & Grafana**: For monitoring the application

## Project's Aim

### Major functions that are implemented:

1. APIs to fetch/add/edit/delete books (H2 Database).
2. APIs to fetch/add/cancel issuing of the books to the customers (H2 Database).
3. Customers to be managed by OAuth2 server (in-memory).

### Non-functional implemented features:

- It should be easy to scale.
- It must be highly secured.
- It should be resilient.
- It should be performant.
- It should support quick releases.

## To Run

### Clone the Repository
First, clone the repository to your local machine:

git clone https://github.com/your_username/your_repository_name.git
cd your_repository_name

###Build the Application
Use Maven to build the application and download all required dependencies:

mvn clean install

###Run the Application
You can run each microservice individually.
Running Locally
To run the application locally, execute the following command in each microservice directory:

mvn spring-boot:run

###Accessing the Application
Once the application is running, you can access the APIs through the following endpoints:
Book Microservice: http://localhost:8081/books
Issue Microservice: http://localhost:8082/issues

##API Documentation
You can view the Swagger documentation for the APIs at:

http://localhost:8080/swagger-ui/

#F#or Sample outputs 
Refer to the word document 

##Note
There are two versions of the project.
Version 2 has additional 'userms' service
You can also dockerize the services to enhance your project
