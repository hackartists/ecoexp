FROM gradle:5.6.2 as build

ADD .  /eco
WORKDIR /eco
RUN gradle build

FROM openjdk:8-alpine
COPY --from=build /eco/build/libs/ecoexp-0.0.1-SNAPSHOT.jar  /app/app.jar

EXPOSE 8080/tcp

CMD ["java","-jar","/app/app.jar"]
