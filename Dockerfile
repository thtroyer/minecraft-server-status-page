# build jar
FROM maven:3.8.5-eclipse-temurin-18 AS build
RUN mkdir /home/app
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml dependency:go-offline -B
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml package


# build image
FROM eclipse-temurin:18
ENV TZ=America/Indiana/Indianapolis
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

