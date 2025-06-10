db = db.getSiblingDB('medilabo');

db.createCollection('notes');
db.notes.insertMany([
    {
        "patientId": 1,
        "content": "Le patient a un fort poids, est fumeur et présente du Cholesterol élevé.",
        "date": "2025-01-01"
    },
    {
        "patientId": 1,
        "content": "Le patient présente maintenant une chute de anticorps et une fatigue persistante.",
        "date": "2025-01-02"
    },
    {
        "patientId": 1,
        "content": "Le patient présente maintenant des vertiges et des douleurs thoraciques.",
        "date": "2025-01-03"
    }
]);