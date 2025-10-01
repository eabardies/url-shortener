# URL Shortener

A simple **URL Shortener API** built with **Spring Boot**.  
It allows you to shorten long URLs and redirect users to the original URL.

This project supports **In-Memory** storage, and is open for extension to other storage implementation.

---

## Features

- Shorten a long URL to a short code
- Redirect short URL to original URL
- Validate URL format
- Error handling (400 for invalid input, 404 for not found)
- Easily switch storage and service using the UrlRepository and UrlShortenerService interfaces, respectively. 
Currently, **In-Memory (HashMap)** storage and InMemoryUrlShortenerService are implemented. 
  - Feel free to add new storage and swap out accordingly.
- Logging using Lombok's slf4j

---

## Technologies Used

- Java 17
- Spring Boot
- JUnit 5 (unit & integration tests)
- Maven
- Lombok

---

## Prerequisites

- Java JDK 17+
- Optional: Maven (if building from source). If installation is needed, refer to this guide: https://maven.apache.org/install.html

---

## Running Locally

Follow these steps to run the URL Shortener API on your local machine.

### 1. Prerequisites

- Verify if **Java JDK 17+** is installed by running the command:
  ```bash
  java -version
  ```
### 2. Cloning the repository
  ```bash
    git clone https://github.com/eabardies/url-shortener.git
    cd url-shortener
  ```  
### 3. Once you have the source code and want to build the JAR:
  - Output: target/url-shortener-0.0.1-SNAPSHOT.jar
    ```bash
    mvn clean package
    ```
### 4. Run the application: 
- Output: target/url-shortener-0.0.1-SNAPSHOT.jar
  ```bash
  java -jar target/url-shortener-0.0.1-SNAPSHOT.jar
  ```
### 5. Testing the application:
  - You may use Postman or Curl. The API endpoints are the following:
    
    - Shorten URL: (POST) http://localhost:8080/api/shorten
    
          POST http://localhost:8080/api/shorten
          Body: "https://www.originenergy.com.au/electricity-gas/plans.html"
    - Redirect to Original URL: (GET) http://localhost:8080/{code}
      
          GET http://localhost:8080/api/{code}



