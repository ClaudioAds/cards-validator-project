# essa primeira parte faz o build usando maven e faz um ALias para o nome Build
FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# essa segunda parte pega a copia da variavel build que cria a nova target e o Jar dentro dela e executa
FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar
EXPOSE 8761
ENTRYPOINT java -jar app.jar
