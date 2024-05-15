# Use a slim JDK 20 image based on Eclipse Temurin
FROM eclipse-temurin:20-jdk-alpine

ARG JAR_FILE=target/*.jar
# Copy the application jar (adjust based on your project structure)
COPY ${JAR_FILE} app.jar

# Expose the port your application runs on (adjust if needed)
# EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
