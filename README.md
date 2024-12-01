# Alumni Management System

The Alumni Management System is a comprehensive platform designed to manage alumni information, events, and other related activities. The project aims to facilitate better communication and engagement between alumni and the institution, providing a centralized platform for managing alumni data, organizing events, and fostering a strong alumni network.

## Table of Contents

- [Objective](#objective)
- [Technologies](#technologies)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Frontend](#frontend)

## Objective
The Alumni Management System aims to provide a robust platform for managing alumni information, organizing events, and facilitating communication between alumni and the institution. The project is designed to enhance alumni engagement, streamline event management, and provide valuable insights into alumni activities and contributions.

## Technologies

   - Frontend:
     
      The frontend of the Alumni Management System is developed using React JS, a popular JavaScript library for building user interfaces. React JS provides a modern and efficient approach to building web applications and allows for seamless updates and modifications.

   - Backend:

      The backend of the Alumni Management System is developed using Spring Boot, a powerful framework for building Java-based applications. Spring Boot provides a scalable and efficient way to handle server-side logic and database interactions.
   
   - Authentication and Authorization:

     Keycloak which is an open source Identity and Access Management solution is used to manage user authentication, authorization, and secure access to the application. This integration ensures that only authorized users can access specific resources and perform certain actions within the system. 

   - Database:
     
      The Alumni Management System uses PostgreSQL, a powerful and reliable relational database, to store and manage data. PostgreSQL provides robust data storage capabilities and supports complex queries and indexing.

   - Containerization:
     
     To streamline the development and management of dependent services, Docker is utilized to run and manage essential services required by the application. This includes:

      - Service Isolation: Each service is containerized, ensuring they run in isolated environments with no conflicts.
      Portability: Docker containers provide a consistent runtime environment, making it easy to replicate the setup across development, testing, and production.
      Ease of Dependency Management: All dependencies are defined in Dockerfile and docker-compose.yml, ensuring seamless setup and consistency for all team members.
      - Quick Setup: With a single docker-compose up command, all dependent services are started and configured, reducing setup time.
     
   

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

     



   









