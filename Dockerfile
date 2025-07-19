# ---------- fase de build ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# copia apenas o que é necessário para resolver dependências
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# copia o código e empacota
COPY src src
RUN mvn -q clean package -DskipTests

# ---------- imagem final ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/ms-chronus-pessoas-*.jar app.jar

# porta padrão Spring Boot
EXPOSE 8080

# JVM flags leves; ajuste se precisar de perfil específico
ENTRYPOINT ["java","-jar","/app/app.jar"]
