FROM openjdk:18-alpine
WORKDIR target/home/app
COPY target/classes /home/app/classes
COPY target/dependency/* /home/app/libs/
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/home/app/libs/*:/home/app/classes/", "com.luckwheat.notes.Application"]
