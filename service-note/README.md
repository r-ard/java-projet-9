# Note Service – MediLabo

Ce service fait partie de l’application **MediLabo** et est dédié à la gestion des **notes médicales des patients**.  

Le service est exposé sur le port `8082` et est disponible dans le service gateway via le préfix `/service-note/`.

---

## Endpoints

| Méthode | URL                           | Description                                 |
|---------|-------------------------------|---------------------------------------------|
| `GET`   | `/status`                     | Vérifie que le service est actif            |
| `GET`   | `/notes`                      | Récupère les notes                          |
| `GET`   | `/notes/{id}`                 | Récupère une note par son id                |
| `GET`   | `/patient-notes/{patientId}`  | Récupère toutes les notes d’un patient |
| `POST`  | `/notes`                      | Crée une nouvelle note |
| `PUT`   | `/notes/{id}`                 | Met à jour une note existante               |
| `DELETE`| `/notes/{id}`                 | Supprime une note par son id     |