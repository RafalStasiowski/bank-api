FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE=build/libs/bank.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]