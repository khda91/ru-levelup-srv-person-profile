# Person Profile Service

## Required tools
* Java 11
* Gradle
* Docker
* PostgreSQL

## Documentation
* [Swagger](https://khda91.github.io/ru-levelp-specs/#)

## Run with gradle
```bash
# build
./gradlew clean build

# run all tests
./gradlew test

# run the app with 'local' profile without tests
./gradlew bootRun --args='--spring.profiles.active=local' -x test
```
## Run with docker
Open project root directory and execute following command
```bash
docker-compose -f docker/docker-compose.yml up
```
