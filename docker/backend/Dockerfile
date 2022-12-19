FROM maven:3.8.6-openjdk-18-slim AS build

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:18-slim-bullseye AS runtime

WORKDIR /app

COPY --from=build /build/target/*.jar ./app.jar
COPY ./docker/backend/docker-entrypoint.sh .

RUN apt update && apt install -y netcat
RUN chmod +x ./docker-entrypoint.sh
RUN sed -i -e 's/\r$//' ./docker-entrypoint.sh

EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["./docker-entrypoint.sh"]