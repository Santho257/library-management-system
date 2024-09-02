FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY ./pom.xml ./

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -Pprod

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=build /app/target/library-management-system-0.0.1-SNAPSHOT.jar /app/lms.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "./lms.jar", "--spring.profiles.active=prod"]