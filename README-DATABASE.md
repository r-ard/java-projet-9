# Base de donnée - MediLabo

Le projet MediLabo contient 2 tables MySQL et un modèle MongoDB.

---

## Comment initialiser les base de données ?

**Warning** : *Il n'est pas nécessaire de suivre ces étapes si vous éxecutez l'application via docker-compose.*

Afin d'initaliser les bases de données du projet pour la première fois, vous devez éxécuter le script SQL `database/mysql/medilabo.sql`.

(Optionnel) Si vous voulez charger les données de fixture de l'application vous devez suivre ces étapes par base de données :
- MySQL : éxecuter le script SQL `database/mysql/fixtures.sql`.
- MongoDB : éxecuter le fichier de fixtures.js comme ceci :
    ```
    mongo < database/mysql/fixtures.js
    ```

---

## Structure - MySQL

### Table `patients` :

Entité patient.

| Colonne        | Type           | Contraintes                     | Description                             |
|----------------|----------------|----------------------------------|-----------------------------------------|
| `id`           | INT            | PRIMARY KEY, AUTO_INCREMENT     | Identifiant unique du patient           |
| `first_name`   | VARCHAR(50)    | NOT NULL                        | Prénom du patient                       |
| `last_name`    | VARCHAR(50)    | NOT NULL                        | Nom de famille du patient               |
| `birth_date`   | DATE           | NOT NULL                        | Date de naissance                       |
| `gender`       | VARCHAR(1)     | NOT NULL, DEFAULT ''            | Sexe (`M`, `F`)                   |
| `address`      | VARCHAR(255)   | NULL                            | Adresse postale                         |
| `phone_number` | VARCHAR(50)    | NULL                            | Numéro de téléphone                     |

### Table `users` :

Entité utilisateur, utilisé par SpringSecurity afin d'authentifié le client.


| Colonne        | Type           | Contraintes                     | Description                         |
|----------------|----------------|----------------------------------|-------------------------------------|
| `id`           | INT            | PRIMARY KEY, AUTO_INCREMENT     | Identifiant unique de l’utilisateur |
| `first_name`   | VARCHAR(50)    | NOT NULL                        | Prénom de l’utilisateur             |
| `last_name`    | VARCHAR(50)    | NOT NULL                        | Nom de famille de l’utilisateur     |
| `username`     | VARCHAR(50)    | NOT NULL                        | Nom d’utilisateur (unique)          |
| `password`     | VARCHAR(255)   | NOT NULL                        | Mot de passe (haché via bcrypt)     |

---

## Structure - MongoDB

### Modèle `notes` :

Modèle d'une note de consultation avec un patient.


| Colonne     | Type         | Description                                    |
|-------------|--------------|------------------------------------------------|
| `_id`       | ObjectId     | Identifiant unique de l’utilisateur            |
| `patientId` | Int32        | Identifiant (MySQL) du patient relié à la note |
| `content`   | String       | Contenue de la note                            |
| `date`      | VARCHAR(50)  | Date la note                                   |