FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./pom.xml
COPY booking-service/pom.xml ./booking-service/pom.xml
COPY booking-service/src ./booking-service/src
COPY checkstyle.xml ./checkstyle.xml
RUN mvn clean package -DskipTests -f booking-service/pom.xml

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/booking-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]