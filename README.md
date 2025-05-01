# Gestion Garage Microservice

Ce projet est un microservice développé avec Spring Boot. Il permet de gérer les véhicules, garages et accessoires pour Renault.

## ⚙️ Tech Stack

- Java 17
- Spring Boot
- Kafka
- H2 database
- Docker / Docker Compose
- JUnit / Mockito
- Spring Boot test
- Api Rest

## 📦 Fonctionnalités

- Création, modification et suppression de garages.
- Récupération d’un garage spécifique (par ID).
- Liste paginée de tous les garages, avec possibilité de tri (par nom, ville, etc.).
- Gestion des garages (max 50 véhicules par garage)
- Ajout, modification et suppression de véhicules associés à un garage.
- Lister les véhicules d’un garage spécifique.
- Possibilité de lister tous les véhicules d’un modèle donné dans plusieurs garages.
- Ajout, modification et suppression d'accessoires associés à un véhicule.
- Lister les accessoires d’un véhicule.
- Rechercher des garages en fonction de critères spécifiques, tels que :
  Type de véhicule pris en charge.
  Disponibilité d'un accessoire particulier dans au moins un véhicule.
- Publication d’événements via Kafka
- API REST sécurisée

## ▶️ Lancer le projet

# Démarrer Kafka et Zookeeper
docker-compose up -d

# Lancer l'application Spring Boot
./mvnw spring-boot:run
