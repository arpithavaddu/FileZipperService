FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/filezipper-0.0.1-SNAPSHOT.jar
RUN mkdir -p /app
COPY ${JAR_FILE} /app/filezipper-0.0.1-SNAPSHOT.jar
RUN mkdir -p /app/uploads
WORKDIR /app
ENTRYPOINT ["java","-jar","filezipper-0.0.1-SNAPSHOT.jar"]