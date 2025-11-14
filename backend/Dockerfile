# Imagem oficial do Maven com Java 21 para compilar o .jar
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia todo o código-fonte do backend para o contêiner
# (O .dockerignore vai pular a pasta 'target' desnecessária)
COPY . .

# Roda o comando do Maven para compilar o projeto e gerar o .jar
# -DskipTests pula os testes para um build mais rápido
RUN mvn clean package -DskipTests

# Usamos uma imagem "limpa" e leve, que só contém o Java 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia APENAS o .jar compilado do estágio "build"
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar ./app.jar

# Expõe a porta em que o Spring Boot roda
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner rodar
ENTRYPOINT ["java", "-jar", "app.jar"]