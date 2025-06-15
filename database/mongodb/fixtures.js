db = db.getSiblingDB('medilabo');

db.createCollection('notes');
db.notes.insertMany([
    {
        "patientId": 1,
        "content": "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé.",
        "date": "2025-01-01"
    },
    {
        "patientId": 2,
        "content": "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement.",
        "date": "2025-01-02"
    },
    {
        "patientId": 2,
        "content": "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale.",
        "date": "2025-01-03"
    },
    {
        "patientId": 3,
        "content": "Le patient déclare qu'il fume depuis peu.",
        "date": "2025-01-04"
    },
    {
        "patientId": 3,
        "content": "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé.",
        "date": "2025-01-05"
    },
    {
        "patientId": 4,
        "content": "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments.",
        "date": "2025-01-06"
    },
    {
        "patientId": 4,
        "content": "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps.",
        "date": "2025-01-07"
    },
    {
        "patientId": 4,
        "content": "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé.",
        "date": "2025-01-08"
    },
    {
        "patientId": 4,
        "content": "Taille, Poids, Cholestérol, Vertige et Réaction.",
        "date": "2025-01-09"
    },
]);