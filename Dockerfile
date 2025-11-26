# --- Estágio 1: Build (para compilar o JAR usando Gradle) ---
# Usamos uma imagem que já tem o Gradle e o JDK 21
FROM gradle:jdk21 AS build
WORKDIR /app
# Copia todo o projeto para o container
COPY . /app
# Executa o comando de build (gera o JAR em build/libs)
RUN ./gradlew clean build -x test

# --- Estágio 2: Package (para rodar a aplicação) ---
# Usamos uma imagem leve de JRE 21 para o runtime
FROM eclipse-temurin:21-jre-jammy
# Expõe a porta que o Spring Boot usa (padrão 8080)
EXPOSE 8080
# Copia o JAR do estágio de build
COPY --from=build /app/build/libs/*.jar app.jar
# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]