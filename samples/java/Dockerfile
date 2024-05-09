# Use a base image with Java and Gradle installed
FROM gradle:latest AS builder

# Set the working directory
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle.kts .

# Copy the source code
COPY src/ src/

# Build the application
RUN gradle build

# Use a lightweight base image for the final image
FROM amazoncorretto:22-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]