FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
ARG PORT_APP=8080
ENV PORT=$PORT_APP
EXPOSE $PORT_APP
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]