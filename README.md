# coppola
Copla is a micro service for payload validation


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
