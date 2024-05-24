# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y locales && \
    locale-gen en_US.UTF-8

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file to the working directory
COPY target/post-service-0.0.1-SNAPSHOT.jar post-service.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "post-service.jar"]
