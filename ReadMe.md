# RestAssured Java Testing

This repository contains API testing examples using RestAssured with Java. The project demonstrates how to write and run API tests for various HTTP methods and endpoints.

## Project Structure


### Description of Directories and Files

- **src/main/java/**: Directory for main application code (if any).
- **src/test/java/**: Directory for test classes and configuration.
  - **tests/**:
    - `CreateUserTests`: Contains test cases for create user api requests.
    - `UpdateUserTests`: Contains test cases for update user api requests.
  - **utils/**: Utility classes for common test functions.
- **pom.xml**: Maven project file that includes project metadata and dependencies.
- **README.md**: Project documentation.

## Getting Started

### Prerequisites

Ensure you have the following installed:
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/)

### Installation

1. Clone the repository:

   git clone https://github.com/rohitmanekar/restassured-java.git
   cd restassured-java

2. Install the dependencies:
   mvn clean install

### Running Tests

1. To run all tests:
   mvn test

2. To run a specific test class, use the following command:
   mvn -Dtest=TestClassName test

Replace TestClassName with the name of the test class you want to run.

### Test Details
The project includes test cases for various HTTP methods:

GET: Tests for retrieving resources.
POST: Tests for creating new resources.
PUT: Tests for updating existing resources.
