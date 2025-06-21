# Patient Service – MediLabo

Ce service fait partie de l’application **MediLabo** et est responsable de la gestion des **informations des patients**.

Le service est exposé sur le port `8081` et est disponible dans le service gateway via le préfix `/service-patient/`.

---

## Endpoints

| Méthode | URL               | Description                                  |
|---------|-------------------|----------------------------------------------|
| `GET`   | `/status`         | Vérifie que le service est actif             |
| `GET`   | `/patients`       | Récupère la liste de tous les patients       |
| `GET`   | `/patients/{id}`  | Récupère un patient selon son id             |
| `POST`  | `/patients`       | Crée un nouveau patient                      |
| `PUT`   | `/patients/{id}`  | Met à jour les données d’un patient existant |
| `DELETE`| `/patients/{id}`  | Supprime un patient selon son id             |

