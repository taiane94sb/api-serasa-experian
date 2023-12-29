FROM openjdk:17

WORKDIR /app

COPY ./target/serasaexperian-api-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "serasaexperian-api-0.0.1-SNAPSHOT.jar"]
