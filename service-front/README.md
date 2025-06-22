# Front Service – MediLabo

Ce service constitue l’**interface utilisateur** de l’application MediLabo en utilisant le moteur de rendu `Thymeleaf`.

Le service est exposé sur le port `8084` et est disponible dans le service gateway via le préfix `/`.

---
## Endpoints

###  AuthController

| Méthode | URL     | Vue               | Description                  |
|---------|---------|-------------------|------------------------------|
| `GET`   | `/login`| `auth/login.html` | Page de connexion            |
| `GET`   | `/`     | `index.html`      | Page d’accueil|

---

### PatientController

| Méthode | URL                                 | Vue                                      | Description                                       |
|---------|-------------------------------------|------------------------------------------|---------------------------------------------------|
| `GET`   | `/patients`                         | `patient/index.html`                     | Liste tous les patients                          |
| `GET`   | `/patients/create`                  | `patient/create.html`                    | Formulaire de création d’un patient              |
| `POST`  | `/patients/create`                  | redirection vers `patients/inspect/{id}` | Création d’un nouveau patient                    |
| `GET`   | `/patients/update/{id}`             | `patient/update.html`                    | Formulaire de modification d’un patient          |
| `POST`  | `/patients/update/{id}`             | redirection vers `/inspect`              | Mise à jour du patient                           |
| `GET`   | `/patients/delete/{id}`             | redirection vers `/list`                 | Suppression d’un patient                         |
| `GET`   | `/patients/inspect/{id}`            | `patient/inspect.html`                   | Détails d’un patient, notes et rapport de diabète|

---

### NoteController

| Méthode | URL                                        | Vue                                            | Description                                |
|---------|--------------------------------------------|------------------------------------------------|--------------------------------------------|
| `GET`   | `/notes/create/{patientId}`                | `note/create.html`                             | Formulaire de création de note             |
| `POST`  | `/notes/create/{patientId}`                | redirection vers `/notes/inspect/${patientId}`                 | Création d’une note pour un patient       |
| `GET`   | `/notes/update/{noteId}`                   | `note/update.html`                             | Formulaire de mise à jour d’une note       |
| `POST`  | `/notes/update/{noteId}`                   | redirection vers `/notes/inspect/${patientId}`                    | Mise à jour d’une note                     |
| `GET`   | `/notes/delete/{noteId}`                   | redirection vers `/notes/inspect/${patientId}` | Suppression d’une note                     |