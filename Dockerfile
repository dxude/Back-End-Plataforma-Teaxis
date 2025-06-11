# Estágio 1: Build da Aplicação
FROM maven:3.9-eclipse-temurin-23-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Imagem Final de Execução
FROM eclipse-temurin:23-jdk-alpine
COPY --from=build /target/*.jar app.jar

# Linha adicionada como boa prática
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]