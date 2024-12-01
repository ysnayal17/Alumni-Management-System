# Alumni Management System

The Alumni Management System is a web application designed to manage alumni information, events, and other related activities. It consists of a backend built with Spring Boot and a frontend built with React.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Frontend](#frontend)
- [Contributing](#contributing)
- [License](#license)

## Features

- User authentication and authorization using Keycloak
- Manage alumni information
- Register and manage events
- Search for events by name and speaker
- Responsive frontend built with React and Tailwind CSS

## Prerequisites

- Java 21
- Node.js and npm
- Docker and Docker Compose
- PostgreSQL

## Installation

1. **Clone the repository:**

   ```sh
   git clone https://github.com/yourusername/Alumni-Management-System.git
   cd Alumni-Management-System

2. Backend Setup:

- Navigate to the backend directory:
  ```sh
  cd alumni-management

- Create a src/main/resources/application.properties file with the following content:
  ```sh
  # Database configuration
  spring.datasource.url=jdbc:postgresql://localhost:5432/alumni_management
  spring.datasource.username=postgres
  spring.datasource.password=yourpassword
  
  # Hibernate configuration
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  
  # Keycloak configuration
  keycloak.auth-server-url=http://localhost:8080/auth
  keycloak.realm=alumni-management
  keycloak.resource=auth-app
  keycloak.credentials.secret=your-client-secret
  keycloak.bearer-only=true
- Build backend
   ```sh
   ./gradlew build

2. Frontend Setup:

- Navigate to the frontend directory
   ```sh
   cd ../Frontend
- Install dependencies
  ```sh
  npm install

## Running the application
1. Start the Keycloak server:
   
- Navigate to the backend directory
  ```
   cd ../alumni-management
  ```

- Start Keycloak using Docker Compose:
  ```
   docker-compose up

  ```
2. Run the backend:
   
- In the backend directory, run:
  ```
   ./gradlew bootRun
  ```
3. Run the frontend:
   - Navigate to the frontend directory:
     ```
     cd ../Frontend
     ```
    - Start the frontend development server:
      ```
      npm start
      ```

4. Access the application:
   - Open your browser and navigate to http://localhost:3000
  
## API Endpoints
- User Registration
   - POST ```/api/user/register```
   - Request Body: ```UserDTO```
   - Description: Register a new user.
     
- Event Registration
  - POST ```/api/event-registrations/register```
    - Request Body: EventRegistrationDTO
    - Description: Register for an event.
  - GET ```/api/event-registrations```
    - Description: Get all event registrations.
      
   - GET ```/api/event-registrations/search```

    - Query Parameters: eventName, speaker
    - Description: Search for event registrations by event name and speaker.
 
## Frontend
The frontend is built with React and Tailwind CSS. It includes the following main components:

- Navbar: The navigation bar.
- Footer: The footer section.
- Home: The home page.
- Login: The login page.
- AlumnisList: The alumni list page.
- Event: The event page.
- JobsList: The jobs list page.

     



   









