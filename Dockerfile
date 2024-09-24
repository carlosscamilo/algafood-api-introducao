FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/*.jar /app/algafood.jar

EXPOSE 8080

CMD [ "java", "-jar", "algafood.jar" ]