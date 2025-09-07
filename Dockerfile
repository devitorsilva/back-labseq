# =========================
# Etapa 1: Build do Maven + Quarkus
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml para cache de dependências
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar todo o código-fonte (backend + frontend)
COPY src ./src

# Build do Quarkus em modo produção (fast-jar)
RUN mvn clean package -DskipTests -Dquarkus.profile=prod

# =========================
# Etapa 2: Imagem final leve
# =========================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar build do Quarkus do estágio anterior
COPY --from=build /app/target/quarkus-app/ ./quarkus-app

# Expor porta padrão do Render
EXPOSE 8080

# Rodar a aplicação em produção
CMD ["java", "-Dquarkus.profile=prod", "-Dquarkus.http.host=0.0.0.0", "-jar", "quarkus-app/quarkus-run.jar"]
