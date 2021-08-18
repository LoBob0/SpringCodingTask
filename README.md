# SpringCodingTask
A small app consuming a REST API service, with a bareback angular app that let the user download
the consumed data in json or xml format

## Tecnology used

- BE: Spring Boot + WebFlux

- FE: Angular

## How To Run

- BE: run the command `mvn spring-boot:run` in the project root, this will run the application on
  the port 8080

- FE: run the following steps, this will run the client on the port 4200:

    - run ` cd src/main/angular`
    - run `ng serve`
    - Visit http://localhost:4200, you should see the angular application running

## BE API Documentation

Visit http://localhost:8080/rest-consumer/swagger-ui.html to access the Swagger UI for the project
