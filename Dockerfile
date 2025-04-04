# # Use an official OpenJDK runtime as a base image
# FROM openjdk:17-jdk-slim
#
# # Set the working directory in the container
# WORKDIR /app
#
# # Copy the JAR file into the container
# COPY target/*.jar app.jar
#
# # Expose the application port
# EXPOSE 8080
#
# # Command to run the application
# ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

