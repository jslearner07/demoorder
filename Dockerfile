FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demobook.jar
ENTRYPOINT ["java","-jar","/demobook.jar"]