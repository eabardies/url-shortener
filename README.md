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
- Unit tests included

---

## Technologies Used

- Java 17
- Spring Boot
- JUnit 5 + Mockito
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
- Verify if **Maven** is installed by running the command:
  ```bash
  mvn -version
  ```
### 2. Cloning the repository
  ```bash
    git clone https://github.com/eabardies/url-shortener.git
    cd url-shortener
  ```  
### 3. Build and run
  ```bash
    mvn spring-boot:run
  ```

### API Endpoints
  - POST /api/shorten: Shorten a URL, provide the original URL in the request body
  - GET  /api/{code}: Redirect to original URL
  - GET  /api/info/{code}: Get the original URL
    
## Configuration

The base URL for shortened links is configurable in `application.yaml`:

```yaml
app:
  shortener:
    base-url: http://short.ly/
```

### Assumptions and notes
- In-memory store (HashMap) is used and data is lost on restart
- Base URL (https://short.ly) is hardcoded but can be externalized via application.yaml
- Codes are unique, random, and non-sequential using Base62 encoding.
