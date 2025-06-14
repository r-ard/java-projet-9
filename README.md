# MediLabo - Plateforme de gestion médicale

MediLabo est une solution composée de plusieurs microservices permettant la gestion des patients, des notes médicales, et l’évaluation du risque de diabète. L’architecture repose sur Spring Boot, Spring Cloud, Eureka pour la découverte de services, et utilise MySQL et MongoDB pour la persistance des données.

## Services et utilitaires

```
.
├── database/                # Scripts d'initialisation des bases de données
│   ├── mysql/               # SQL pour MySQL (patients, users)
│   └── mongodb/             # Fixtures pour MongoDB (notes)
├── eureka-server/           # Serveur Eureka pour la découverte des microservices
├── service-patient/         # Microservice gestion des patients (Spring Boot, MySQL)
├── service-note/            # Microservice gestion des notes médicales (Spring Boot, MongoDB)
├── service-diabetesrisk/    # Microservice d'évaluation du risque de diabète
├── service-gateway/         # API Gateway (Spring Cloud Gateway)
├── service-front/           # Frontend web (Spring Boot, Thymeleaf)
└── docker-compose.yaml                # Fichier de configuration docker-compose.
```

## Légende des services

- **eureka-server** : Point central de découverte des services (Eureka).
- **service-patient** : CRUD sur les patients, stockage en base avec MySQL.
- **service-note** : CRUD sur les notes médicales, stockage en base avec MongoDB.
- **service-diabetesrisk** : Analyse les notes et données patient pour générer un rapport de risque de diabète.
- **service-gateway** : Gestionnaire du routage, serveur web exposé au client.
- **service-front** : Interface utilisateur web pour la gestion des patients, notes et visualisation des rapports.

---

## Démarrage rapide (dev environment)

1. **Configurer les bases de données** : lancer les scripts de création de schéma et fixture dans `database/mysql` et `database/mongodb`.
2. **Lancer Eureka Server** : démarrer le service `eureka-server`.
3. **Lancer les microservices** : démarrer les services `service-patient`, `service-note`, `service-diabetesrisk`, puis `service-gateway`.
4. **Lancer le frontend** : démarrer `service-front` et accéder à l’interface web.
5. **Connectez vous au frontend** : Via l'url `http://localhost:8080`.

## Lancement via Docker-Compose

1. **Construire les images** : Executer la commande `docker-compose build`.
2. **Lancer les services** : Executer la commande `docker-compose up`.
3. **Connectez vous au frontend** : Via l'url `http://localhost:8080`.

## Utilisateur test

Pour accéder à l'application vous pouvez utiliser l'utilisateur par défaut présent dans les fixtures :

- **Username** : `TestUser`
- **Password** : `azerty123`

---

# Normalisation des données (3NF)

Les base de données du projet ont été conçue selon les principes de la norme (3NF).

### MySQL

- Tables existantes :
  - `patients` : Informations personnelles des patients (nom, prénom, date de naissance, genre, adresse et téléphone).
  - `users` : Utilisateurs autorisés à se connecter à l'application (nom, prénom, nom d'utilisateur, mot de passe).


- Respect de la norme **3NF** :
  - Chaque colonne contient une valeur de type atomique.
  - Chaque colonne dépend de la clé primaire.
  - Aucune colonne ne dépend d'une autre colonne qui n'est pas la clé.
  
### MongoDB
- Model existant :
  - `notes` : Note d'une consultation avec un patient (patientId, contenu, date de la note).

- Respect de la norme **3NF** :
  - Chaque colonne contient une valeur de type atomique.
  - Chaque colonne dépend de la clé primaire.
  - Aucune colonne ne dépend d'une autre colonne qui n'est pas la clé -> *(nous passons par `patientId` afin de récupérer les informations du patient, nous ne stockons pas directement les informations du patient dans le modèle `notes`).*

---

# GreenCode

## Directives appliquées

Voici les directives appliquées au sein de l'application

#### Dockerisation
- Utilisation d'image Docker légère, exemple avec l'utilisation de l'image `eclipse-temurin:17-jre-alpine` pour le run.
- Dockerfile avec de multiples étapes de "build" afin d'avoir une image finale plus légère.



## Suggestions

Voici quelques suggestions GreenCode à appliquer afin d'améliorer l'application :

#### Optimisation du front-end
- Passer à un framework front-end (ex: React, Angular) afin de ne pas effectuer de re-rendu des vues inutile par le back-end.

#### Optimisation base de donnée
- Choisir uniquement un type de base de donnée afin d'héberger nos données uniquement sur un seul serveur afin de limité impacte écologique.

#### Compression et caching
- Ajouter un système de compression HTTP (ex: GZIP) entre les microservices, voir même entre le service front-end et le client.
- Ajouter un système de cache API entre les microservices.

#### Architecture du projet
- Vu l'empleur du projet il serait possible et pertinent d'un point de vue "GreenCode" de passer à une architecture monolithique afin de limité la complexité de code de d'achitecture, la consommation de ressources et le coût en infrastructure.