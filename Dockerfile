# Stage 1: Build the app
FROM maven:3.8.6-openjdk-8 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=build /app/target/helloworld-1.1.jar app.jar

# Set the default port
ENV PORT 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
