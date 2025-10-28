# Stage 1: build with Maven (JDK 21)
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
# copy source
COPY src ./src
# build the WAR
RUN mvn -q -T 1C clean package -DskipTests

# Stage 2: run with Tomcat (JDK 21)
FROM tomcat:10.1-jdk21
# remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*
# copy the built WAR from the builder stage
COPY --from=build /build/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]