# Notes Storage Service
## Overview
  WIP...

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
