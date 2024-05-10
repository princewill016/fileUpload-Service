---File Upload Service.
This project is a RESTful API that allows users to make two HTTP requests, the GET and POST. It provides endpoints for user to upload file and also retrieve uploaded files using the GET request.

---Key Features
File Uploading
File retrieval
--CRUD Operations for file
POST file: Endpoint to add new file with detail like file-name.
GET file: Endpoints to retrieve uploaded file.
Delete file: Endpoint to remove a file from the database.

---Technical Requirements
Backend Framework
Spring Boot: Backend framework for building robust and scalable RESTful APIs.
ORM & Database
Hibernate: Object-Relational Mapping (ORM) framework for interacting with the MySQL database.

---API Documentation
Swagger: API documentation tool for generating interactive API documentation.

---Getting Started
Clone the repository.
Set up MySQL database and configure database connection in application.properties.
Build and run the application.
Access API documentation using Swagger UI.

---API Endpoints
--File Upload Service
GET /api/v2/file: Get file.
POST /api/v2/upload: Upload a new file.
DELETE /api/v2/file/{fileId}: Delete a file.

---API Documentation
Access the API documentation and try out the endpoints using Swagger UI.

---Technologies Used
Spring Boot
Hibernate
MySQL
Spring Security
Swagger

---Contributors
Peter David.
