# Notes App 
## Description
The project provides a service for creating/editing/searching notes. 
You can use it via a web application. The application is available on https://notes.panfutov.com 
## Overview
The project consists of the backend service (Java 18, Micronaut) and frontend application (React).
## Architecture
...
## CI
... 
## Run tests 
`mvn clean test`
<br>
_Note: Docker environment is required to run tests_
## Build Docker Image
Prepare dockerfile and dependencies <br>
`mvn clean mn:dockerfile -Dpackaging=docker` <br>
Build the artifacts <br>
`mvn install -Dpackaging=jar` <br>

## Push Docker Image 
`mvn dockerfile:push`
