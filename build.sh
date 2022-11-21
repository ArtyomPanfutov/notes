#!/bin/sh

if [ -z "${JAVA_HOME}" ]; then
  export JAVA_HOME=$(/usr/libexec/java_home -v18)
  echo "JAVA_HOME ${JAVA_HOME}"
fi
mvn clean mn:dockerfile -Dpackaging=docker
mvn install -Dpackaging=jar