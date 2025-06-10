# Étape 1: Construire le projet
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -Pdocker

# Étape 2: Créer une image distincte pour exécuter l'application
FROM openjdk:11-jre-slim
WORKDIR /app

# Copier le fichier JAR construit depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Définir des variables d'environnement pour le profil et le port
ENV SPRING_PROFILES_ACTIVE=docker

# Exposer le port
EXPOSE 8761

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]