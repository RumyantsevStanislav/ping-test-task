FROM eclipse-temurin:17-jdk-jammy
RUN apt-get update && apt-get install -y iputils-ping
CMD bash
ENV app.name="ping_backend"
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw
COPY src ./src
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]
