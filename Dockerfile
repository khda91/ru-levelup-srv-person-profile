#---------------------build artifact
FROM gradle:7.3-jdk11 AS temp_build

COPY --chown=app:app . /home/app/

WORKDIR /home/app/

RUN gradle build --no-daemon -x test

#---------------------build docker image
FROM openjdk:11.0.13-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=temp_build /home/app/build/libs/*.jar /app/ru-levelp-srv-person-profile.jar


ENTRYPOINT ["java", "-jar", "/app/ru-levelp-srv-person-profile.jar"]
