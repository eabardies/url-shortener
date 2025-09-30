# URL Shortener

A simple **URL Shortener API** built with **Spring Boot**.  
It allows you to shorten long URLs and redirect users to the original URL.

This project supports multiple storage backends: **In-Memory** (default) and **Redis**.

---

## Features

- Shorten a long URL to a short code
- Redirect short URL to original URL
- Validate URL format
- Error handling (400 for invalid input, 404 for not found)
- Easily switch storage between **In-Memory** and **Redis**

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Data Redis
- JUnit 5 (unit & integration tests)
- Maven
- Redis (optional, for alternative storage)

---

## Project Structure


---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/url-shortener.git
cd url-shortener

