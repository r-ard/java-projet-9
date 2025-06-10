# 1. Install dependencies
FROM maven:3.8.4-openjdk-17 AS dependency
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /build

# Get dependencies from the dependency stage
COPY --from=dependency /root/.m2 /root/.m2
COPY pom.xml .

COPY src ./src
RUN mvn package -DskipTests

# 3. Run the application
FROM eclipse-temurin:17-jre-alpine AS runner
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /build/target/*.jar app.jar

# Set the spring profile to 'docker'
ENV SPRING_PROFILES_ACTIVE=docker

# Expose the server port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]