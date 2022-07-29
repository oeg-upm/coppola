# Coppola valiation service

Coppola is a micro service directory for SHACL shapes. It provides a graphical interface for users which scope is help them managing shapes (creating, updating, deleting, or reading) and, also, apply these shapes by means of a Playground to sample payloads. In addition, Coppola publishes a REST API so third-party services can directly use its functionalities.


# Quickstart

Using docker run docker-compose with the following recipe

```yml
version: '2'
services:
  coppola:
    image: acimmino/coppola:latest
    volumes: 
      - type: volume
        source: coppola-db
        target: /coppola/app
        volume: {}
    ports:
      - '4567:4567'

volumes:
  coppola-db:
    name: validation-db
```

Using [java download the latest released version](https://github.com/oeg-upm/coppola/releases) and run it with `java -jar`


# REST API

# API
| Endpoint | Method | Description |
|--|--|--|
| `/api`  |  `GET` | Returns the list of SHACL shapes stored in Coppola |
| `/api/:id`  |  `GET` | Returns the SHACL shape stored in Coppola with the provided `:id`  |
| `/api/:id`  |  `PUT` | Stores a SHACL shape provided in the `body`of the request with the specified `:id`   |
| `/api/:id`  |  `DELETE` | Deletes the SHACL shape stored in Coppola with the provided `:id`   |
| `/api/:id`  |  `POST` | Validates a sample payload provided in the `body`of the request using the SHACL shape related to the provided `:id`. The request must specify the format of the sample payload using the argument format, in case is a JSON-LD 1.1 payload (`?format=json-ld 1.1`) or Turtle (`?format=turtle`) |
