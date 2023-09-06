FROM maven:3.8.6-openjdk-18
WORKDIR webPremierw
COPY pom.xml .
COPY src /webPremierw/src

RUN mvn -f pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/web-app-0.0.1-SNAPSHOT.jar"]