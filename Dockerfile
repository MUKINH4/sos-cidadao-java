# Stage 1: Build the application
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven wrapper and POM, download dependencies
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code and package without tests
COPY src src
RUN chmod +x mvnw && ./mvnw clean package -DskipTests -q

# Stage 2: Create lightweight runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar

# Create non-root user and group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set ownership and drop root privileges
RUN chown -R appuser:appgroup /app
USER appuser:appgroup

# Expose application port
EXPOSE 8080

# Default startup command
ENTRYPOINT ["java", "-jar", "app.jar"]