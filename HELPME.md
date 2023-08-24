# GitApp - Recruitment Project

## Overview

GitApp is a Spring Boot application designed to interact with the GitHub API. It provides endpoints to fetch repository and branch details for a given GitHub username.

## Setup & Running

### Prerequisites

- Java 11 or higher
- Maven (for building the project)

### Steps to Run

1. **Clone the repository**:
   ```
   git clone [repository-url]
   cd [repository-dir]
   ```

2. **Build the project**:
   ```
   mvn clean install
   ```

3. **Run the application**:
   ```
   java -jar target/gitapp-0.0.1-SNAPSHOT.jar
   ```

   Alternatively, if you're using Maven:
   ```
   mvn spring-boot:run
   ```

4. The application will start and be accessible at `http://localhost:8080`.

## Endpoints

- **Fetch Repositories for a User**:
  ```
  GET /github/repositories/{username}
  ```

  Replace `{username}` with the desired GitHub username. The response will be in JSON format.

## Error Handling

- If the provided username does not exist on GitHub, a `404 Not Found` status is returned with a message.
  
- If the `Accept` header is set to `application/xml`, a `406 Not Acceptable` status is returned, indicating that only JSON format is supported.

## Notes

- Ensure you have a stable internet connection, as the application interacts with the GitHub API.
  
- Rate limits may apply when making frequent requests to the GitHub API.
