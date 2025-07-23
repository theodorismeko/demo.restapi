# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only what is needed for the build
COPY pom.xml .
COPY src ./src

# Now run the build
RUN mvn clean package -DskipTests

# Stage 2: Run with slim Java base image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
