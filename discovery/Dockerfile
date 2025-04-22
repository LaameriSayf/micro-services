# Utiliser une image Maven pour construire le projet
FROM maven:3.8.4-openjdk-17-slim as builder

# Répertoire de travail pour Maven
WORKDIR /app

# Copier les fichiers pom.xml et les sources du projet
COPY pom.xml /app
COPY src /app/src

# Construire le projet (génère le fichier JAR)
RUN mvn clean package

# Utiliser une image JDK pour exécuter l'application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le JAR généré depuis l'image précédente
COPY --from=builder /app/target/*.jar discovery.jar

# Exposer le port
EXPOSE 8761

# Lancer l'application
ENTRYPOINT ["java", "-jar", "discovery.jar"]
