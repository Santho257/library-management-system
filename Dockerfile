FROM openjdk:17-jdk

WORKDIR /app

COPY ./target/library-management-system-0.0.1-SNAPSHOT.jar /app/lms.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "./lms.jar"]