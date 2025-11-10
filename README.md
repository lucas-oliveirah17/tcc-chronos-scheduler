# Sistema de Agendamento para Barbearias - TCC

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring](https://img.shields.io/badge/Spring_Boot-3.5.7-green?logo=spring)
![React](https://img.shields.io/badge/React-19.2-blue?logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

> Aplicação web para otimizar a gestão de agendamentos em barbearias. Substitui o controle manual por um painel de admin (Java/Spring) e autoatendimento ao cliente (React). Projeto de TCC do curso de Análise e Desenvolvimento de Sistema (IFSP - Guarulhos).

**🛠️ Status do Projeto:** Em Desenvolvimento

---

## 📚 Índice

* [Sobre o Projeto](#-sobre-o-projeto)
* [Funcionalidades Principais](#-funcionalidades-principais)
* [Arquitetura](#-arquitetura)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Como Executar o Projeto (Ambiente Local)](#-como-executar-o-projeto-ambiente-local)
* [Autores](#-autores)
* [Licença](#-licença)

---

## 📖 Sobre o Projeto

Este projeto é o Trabalho de Conclusão de Curso (TCC) do curso de **Análise e Desenvolvimento de Sistemas do IFSP - Campus Guarulhos**.

O objetivo é desenvolver uma solução de software moderna (uma Single Page Application) para sanar as ineficiências do agendamento manual em barbearias, oferecendo uma plataforma digital que centraliza a gestão para o administrador e proporciona conveniência para o cliente.

---

## ✨ Funcionalidades Principais

O sistema foi modelado com três perfis de usuário, cada um com suas permissões específicas:

* **Para Administradores:**
    * Gestão completa de Serviços (CRUD).
    * Gestão completa de Profissionais (CRUD).
    * Configuração dos horários de funcionamento da barbearia.
    * Visualização e gerenciamento da agenda completa de todos os profissionais.
    * Gestão dos cadastros de clientes.

* **Para Barbeiros (Profissionais):**
    * Login seguro.
    * Visualização da agenda pessoal.
    * Gerenciamento de disponibilidade (para almoço, etc.).
    * Adição de anotações privadas sobre clientes.

* **Para Clientes:**
    * Cadastro e Login na plataforma.
    * Recuperação de senha.
    * Edição do seu próprio perfil.
    * Exploração dos serviços e profissionais disponíveis.
    * Realização de um novo agendamento em um horário vago.
    * Cancelamento de agendamentos futuros.
    * Visualização do histórico de serviços realizados.
    * Avaliação dos serviços concluídos.

---

## 📐 Arquitetura

A solução utiliza uma arquitetura de aplicação web desacoplada, composta por:

1.  **Front-end (SPA):** Uma *Single Page Application* construída em **React**, responsável por toda a interface e experiência do usuário.
2.  **Back-end (API):** Uma **API RESTful** robusta, construída em **Java com Spring Boot**, que centraliza todas as regras de negócio e a segurança.
3.  **Banco de Dados:** Um banco de dados relacional **PostgreSQL**, que persiste todos os dados da aplicação.

---

## 💻 Tecnologias Utilizadas

Este projeto foi construído com as seguintes tecnologias:

* **Back-end:**
    * Java 21 (LTS)
    * Spring Boot 3.5.7
	* Spring Data JPA (Hibernate)
    * Spring Security
	* Java JWT (da AuthO) - Geração de tokens
    * Maven
* **Front-end:**
    * React.js 19.2
	* Vite (com SWC) - Servidor de desenvolvimento e build tool
	* React Router DOM - Gerenciamento de rotas e páginas
    * Axios - Comunicação com a API RESTful
* **Banco de Dados:**
    * PostgreSQL 18
* **Ambiente e DevOps:**
    * Docker / Docker Compose
    * Git / GitHub
    * Postman - Testes de API
* **IDEs:**
    * Eclipse IDE
      * Plugin: editorconfig-eclipse 0.x
    * Visual Studio Code
    * DBeaver

---

## 🚀 Como Executar o Projeto (Ambiente Local)

Para rodar este projeto em sua máquina local, siga os passos abaixo. O projeto é divido em `backend` (a API em Java/Spring) e `frontend` (a aplicação em React/Vite);

**Pré-requisitos:**
* [Git](https://git-scm.com/)
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (para o banco de dados)
* [JDK 21 (OpenJDK)] (https://adoptium.net/))
* [Node.js 24 (LTS)](https://nodejs.org/en/blog/release/v24.11.0) (Use o [NVM](https://github.com/nvm-sh/nvm) para gerenciar)
* IDE Java ([Eclipse](https://www.eclipse.org/downloads/packages/release/2025-09/r/eclipse-ide-java-developers)) e editor de código ([VS Code](https://code.visualstudio.com/download))

**1. Clonar o Repositório**
```bash
git clone https://github.com/lucas-oliveirah17/tcc-barber-scheduler.git
cd tcc-barber-scheduler
```

**2.Configurar Variáveis de Ambiente:**

**a. Para o Docker (Banco de Dados):**
* Na raiz do projeto (`tcc-barber-scheduler`), crie um arquivo chamado `.env`.
* Copie o conteúdo de `.env.example` para o `.env` e define sua senha:
```bash
DB_USER=barber_admin
DB_PASS=sua_senha_aqui
DB_NAME=barber_db
```

**b. Para o Spring Boot (Aplicação Java):**
* Navegue até a pasta `backend/src/main/resources/`.
* Crie um novo arquivo chamado `application-local.properties`.
* Cole o seguinte conteúdo e use a **mesma senha** definida no `.env`:
```bash
# Credenciais para Conexao com o Banco de Dados (Docker)
spring.datasource.url=jdbc:postgresql://localhost:5432/barber_db
spring.datasource.username=barber_admin
spring.datasource.password=sua_senha_aqui
```


**3. Iniciar o Banco de Dados (Docker):**
* No terminal, na raiz do projeto, suba o contêiner do PostgreSQL. 
```bash
docker-compose up -d
```
* O Docker irá baixar a imagem do PostgreSQL e iniciar o banco de dados em background.

**4. Rodar o Back-end (Java/Spring):**
* Abra a pasta `backend` no Eclipse IDE como projeto Maven.
* Aguarde o Maven baixar as dependências do `pom.xml`.
* Encontre a classe principal de inicizalização `BarberSchedulerApplication.java`.
* Execute este arquivo como uma Aplicação Java.
* O servidor estará rodando em `http://localhost:8080`.

**5.Rodar o Front-end (React):** 
* Em um terminal separado, navegue até a pasta `frontend`
* Instale as dependências (apenas na primeira vez):
```bash
npm install
```
* Inicie o servidor de desenvolvimento do Vite:
```bash
npm run dev
```
* O terminal mostrará que a aplicação estará rodando. App estará disponível no navegador `http://localhost:5173`.
---

## 🎓 Autores
- **Lucas Silva de Oliveira** - Desenvolvedor Back-end
   
   [![GitHub](https://img.shields.io/badge/GitHub-100000?style=plastic&logo=github&logoColor=white)](https://github.com/lucas-oliveirah17)
   [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=plastic&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/lucas-oliveirah17/)

- **Daniel Navarro Porto** - Desenvolvedor Front-end & Segurança

   [![GitHub](https://img.shields.io/badge/GitHub-100000?style=plastic&logo=github&logoColor=white)](https://github.com/danielnporto)
   [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=plastic&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/danielnporto/)

---

## 📄 Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
