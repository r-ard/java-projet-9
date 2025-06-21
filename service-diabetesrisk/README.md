# Diabetes Risk Service – MediLabo

Ce service fait partie de l'application **MediLabo** et est dédié à l'évaluation du **risque de diabète de type 2** chez un patient.

Le service est exposé sur le port `8083` et est disponible dans le service gateway via le préfix `/service-diabetesrisk/`.

---

## Endpoints

| Méthode | URL                          | Description                                       |
|---------|------------------------------|---------------------------------------------------|
| `GET`   | `/diabetes-risk/{patientId}` | Génère un rapport de risque pour un patient donné |
