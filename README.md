# Workplanner - backend

## Installation

This application is a Spring Boot application communicating with a MySQL database. Right now it runs on Java 11.

You could install JDK 11 and a MySQL server and run this in a traditional way, but the fastest way to get up and running is by using Docker.

### Docker

Install Docker and docker-compose. When these two tools are installed, you can start the backend and database by one command.

If you have never built the application before, you must run the following command

    mvn clean install -U
    
After a hopefully successful build process, you can start the application by running

    docker-compose up
    
The command above starts both the database and the spring boot application.
There are two images, called "api" and "db". To start just one of them, type

    docker-compose up db
    
or

    docker-compose up api
    
## API documentation

You can find documentation for the REST api here: ./target/generated-docs/index.html.

If the documentation haven't been created yet, you must run the maven command above.


