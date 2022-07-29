FROM eclipse-temurin:11

RUN apt-get update && apt-get install -y wget
RUN mkdir app
WORKDIR /app
RUN wget https://github.com/oeg-upm/coppola/releases/download/v0.1.0/coppola-0.1.0.jar

EXPOSE 4567

CMD ["java", "-jar", "./coppola-0.1.0.jar"]