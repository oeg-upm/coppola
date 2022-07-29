FROM eclipse-temurin:11

RUN apt-get update && apt-get install -y wget
RUN mkdir app
WORKDIR /app
COPY ./coppola-*.jar ./coppola.jar
EXPOSE 4567

CMD ["java", "-jar", "./coppola-0.1.0.jar"]