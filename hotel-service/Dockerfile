FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./pom.xml
COPY hotel-service/pom.xml ./hotel-service/pom.xml
COPY hotel-service/src ./hotel-service/src
RUN mvn clean package -DskipTests -f hotel-service/pom.xml

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/hotel-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]