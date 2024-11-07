# VOTP (Validation OTP)

## Description

VOTP is a robust web application developed with Spring Boot, designed to generate and validate One-Time Passwords (OTP) using REST APIs. This project offers high scalability by leveraging Redis caching (when the profile is enabled) to efficiently manage OTP storage and retrieval.

## Key Features

- Secure OTP generation and validation
- OTP request and validation throttling for enhanced security
- Highly scalable architecture
- Flexible caching options with Redis and Guava cache

## Technologies Used

- Java 17
- Spring Boot
- Redis (for caching when profile is enabled)
- Guava Cache (alternative caching solution)
- RESTful API design

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven
- Redis server (required if Redis profile is enabled)

## Installation

1. Clone the repo:
- git clone git@github.com:VishvendraShukla/votp.git
2. Open the project in your favorite IDE(preferrably Intellij Idea CE)
3. Open terminal on your IDE and clean install the project
- mvn clean install
4. Run the project using
- mvn spring-boot:run

## Configuration

### Redis Configuration
For instructions on setting up Redis caching, refer to the [Redis Configuration Guide](./docs/REDIS_CONFIG.md).

## Contributing

This is a self-made project and is not currently open for external contributions.

## Additional Information

- The project uses a dual caching strategy with Redis and Guava cache for optimal performance and scalability.
- OTP generation and validation processes are designed with security as a top priority.
- The application is built to handle high concurrency and can be easily scaled horizontally.

For more information or support, please contact me at shukla.vishvendra14@gmail.com.
