# Notes Storage Service
## Overview
  WIP...
## Run tests 
`mvn clean test`
<br>
_Note: Docker environment is required to run tests_
## Build the project 
`mvn clean install`
## Build Docker Image
Prepare dockerfile and dependencies <br>
`mvn clean mn:dockerfile -Dpackaging=docker` <br>
Build the artifacts <br>
`mvn install` <br>
Build docker image <br>
 `mvn dockerfile:build`

## Push Docker Image 
`mvn dockerfile:push`
