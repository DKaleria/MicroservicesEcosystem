FROM maven:3.8.6-amazoncorretto-17 AS build
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn package -DskipTests

#Run stage
FROM openjdk:17-alpine
ARG JAR_FILE=/build/target/*.jar
COPY --from=build $JAR_FILE /opt/docker-auth_user/auth_user.jar
ENTRYPOINT ["java", "-jar", "/opt/docker-auth_user/auth_user.jar"]

#FROM maven:3.8.6-amazoncorretto-17
#WORKDIR /build/
#COPY pom.xml ./
#RUN mvn dependency:go-offline
#COPY src ./src/
#RUN mvn package -DskipTests
#RUN mkdir -p /opt/app/
#RUN cp $(find ./target/ -name '*.jar') /opt/app/app.jar
#ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]