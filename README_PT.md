# LabSeq API

API para cálculo da sequência LabSeq com cache em memória, implementada em Quarkus.

---

## 1️⃣ Source Code (Resumo)

O projeto contém os principais arquivos: DTO (`LabseqResponsetDTO.java`), Services (`LabseqService.java`, `CacheService.java`), Resource/Controller (`BackLabseq.java`), Maven POM (`pom.xml`) e Dockerfile para build e execução em container.

## 2️⃣ REST API Documentation – OpenAPI (Swagger)

A documentação é gerada automaticamente pelo Quarkus + SmallRye OpenAPI.

Endpoints disponíveis:

- GET `/labseq/{n}`: Calcula a sequência LabSeq para `n`.
- GET `/labseq/clean-cache`: Limpa o cache da sequência.

Swagger UI: http://localhost:8080/swagger-ui  

Exemplo de resposta JSON do endpoint `/labseq/10`:

{
"transactionTime": 3,
"error": "",
"result": "12"
}

---

## 3️⃣ Execution Instructions

### Via Docker (recomendado)

Dockerfile completo:

# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/quarkus-app/ ./
EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar"]

Para buildar a imagem Docker:

docker build -t labseq-app .

Para rodar o container:

docker run -i --rm -p 8080:8080 labseq-app

- A API estará disponível em http://localhost:8080/labseq/{n} e http://localhost:8080/labseq/clean-cache
- Swagger UI: http://localhost:8080/swagger-ui
- OpenAPI JSON: http://localhost:8080/openapi

### Execução Local (desenvolvimento)

Rodar sem Docker:

./mvnw quarkus:dev

- Hot reload ativado (alterações refletem imediatamente)
- Endpoints e Swagger nos mesmos URLs acima

---

## 4️⃣ Observações

- `transactionTime` indica o tempo de execução em milissegundos.
- Em caso de cálculo grande, pode retornar parcialmente com `error` informando timeout.
- DTO usado para todas respostas JSON: `LabseqResponsetDTO`

---

## 5️⃣ Exemplo de Uso

GET /labseq/10:

{
"transactionTime": 2,
"error": "",
"result": "12"
}

GET /labseq/clean-cache:

{
"transactionTime": 1,
"error": "",
"result": ""
}
