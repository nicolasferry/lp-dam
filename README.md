# Gestion de projet - LP DAM - IUT Nice

Ce depôt contient toutes les ressources pour bien démarrer votre projet. 

## Content

En particulier il contient:
* Un squelette de l'application Android pour pousser les formulaires et les données des capteurs du smartphone. Il vous est fourni pour l'exemple le code pour l'acqusition des données géolocalisation ainsi que le code pour pousser ces données vers FIWARE.

* La description swagger de l'API de FIWARE Orion.

* Un fichier docker compose ainsi qu'un script start.sh pour lancer la partie serveur (i.E., FIWARE, etc.)

* Une page HTML simple comme exemple d'UI pour les utilisateurs de notre solution (i.e., nurse, doctor, next of kin, etc.)

* Un script gradle pour construire votre application.

## Getting started

**0. Posez des questions !**
1. Installer Docker et Docker-compose.
2. Lancer le script start.sh. A partir d'ici notre serveur est opérationel.
3. Générer la librairie avec le client FIWARE.
4. Si cela n'est pas déjà fait, ajouter la librairie à votre projet gradle.
5. Construire et lancer votre application.

## FIWARE API

La documentation de l'API du FIWARE Orion Context Broker est disponible (ici) [https://fiware-orion.readthedocs.io/en/latest/user/walkthrough_apiv2/index.html#starting-the-broker-for-the_tutorials] 

Nous utiliserons principalement les méthodes suivantes:

### Créer une entité
#### POST /v2/entities
```
curl localhost:1026/v2/entities -s -S -H 'Content-Type: application/json' -d @- <<EOF
{
  "id": "Room1",
  "type": "Room",
  "temperature": {
    "value": 23,
    "type": "Float"
  },
  "pressure": {
    "value": 720,
    "type": "Integer"
  }
}
EOF
```

### Obtenir les informations sur une entité
#### GET /v2/entities/{id} 
```
curl localhost:1026/v2/entities/Room1?type=Room -s -S -H 'Accept: application/json'

```

### Mettre à jour une entité
#### PUT /v2/entities/{id}/attrs/{attrName}/value

```
curl localhost:1026/v2/entities/Room1/attrs/temperature/value -s -S -H 'Content-Type: text/plain' -X PUT -d 28.5
