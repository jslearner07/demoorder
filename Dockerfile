FROM openjdk:11
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demoorder.jar
ENTRYPOINT ["java","-jar","/demoorder.jar"]