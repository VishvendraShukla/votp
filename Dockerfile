# Stage 1: Build Stage
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app

# Copy only the necessary files for dependency resolution first
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the rest of the project files and build the project using the 'prod' profile
COPY src ./src
RUN mvn clean package -Pprod -DskipTests  # Build with prod profile

# Stage 2: Runtime Stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built production JAR from the build stage
COPY --from=build /app/target/votp-0.0.1-SNAPSHOT-prod.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the production application
ENTRYPOINT ["java", "-jar", "app.jar"]
