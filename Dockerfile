FROM maven:3-amazoncorretto-8 AS build
WORKDIR /app
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package

FROM tomcat:9-jre8
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
# CMD ["java", "-jar", "/usr/local/jetty/start.jar"]