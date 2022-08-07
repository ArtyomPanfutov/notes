#!/bin/sh

echo "Building..."
echo "Java: $(java -version)"

mvn clean mn:dockerfile -Dpackaging=docker
mvn install
mvn dockerfile:build