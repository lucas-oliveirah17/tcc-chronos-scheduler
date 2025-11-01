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
    * Spring Security (para autenticação JWT)
    * Spring Data JPA (Hibernate)
    * Maven
* **Front-end:**
    * React 19.2
    * Axios (para comunicação com a API)
* **Banco de Dados:**
    * PostgreSQL 18
* **Ambiente e DevOps:**
    * Docker / Docker Compose
    * Git / GitHub
    * Postman (para testes de API)
* **IDEs:**
    * Eclipse IDE
    * Visual Studio Code

---

## 🚀 Como Executar o Projeto (Ambiente Local)

Para rodar este projeto em sua máquina local, siga os passos abaixo.

**Pré-requisitos:**
* [Git](https://git-scm.com/)
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (para o banco de dados)
* JDK 21 (Ex: [OpenJDK](https://adoptium.net/))
* Node.js 24 (LTS) (Use o [NVM](https://github.com/nvm-sh/nvm) para gerenciar)
* Sua IDE Java (Eclipse) e seu editor de código (VS Code)

**1. Clonar o Repositório**
```bash
git clone https://github.com/lucas-oliveirah17/tcc-barber-scheduler.git
```

**2.Configurar Variáveis de Ambiente:**
* Na raiz do projeto, crie um arquivo chamado `.env`.
* Copie o conteúdo de `.env.example` (vocês devem criar este arquivo modelo) para o `.env` e preencha as variáveis:
```bash
DB_USER=barber_admin
DB_PASS=sua_senha_aqui
DB_NAME=barber_db
```

**3. Iniciar o Banco de Dados (Docker):**
* Este comando irá baixar a imagem do PostgreSQL e iniciar o banco de dados em background.
```bash
docker-compose up -d
```

**4. Rodar o Back-end (Java/Spring):**
* Abra a pasta do back-end (ex: `/backend`) no Eclipse (ou seu IDE Java).
* Aguarde o Maven baixar as dependências.
* Rode o projeto como uma Aplicação Spring Boot (ex: `BarberSchedulerApplication.java`).
* O servidor estará rodando em `http://localhost:8080`.

**5.Rodar o Front-end (React):** 
* Em um terminal separado, navegue até a pasta do front-end (ex: `/frontend`):
```bash
cd frontend
npm install  # Instala as dependências (só na 1ª vez)
npm start    # Inicia a aplicação
```
* O app estará disponível no seu navegador em `http://localhost:3000`.

---

## 🎓 Autores
- **Lucas Silva de Oliveira** - Desenvolvedor Back-end
   - [GitHub](https://github.com/lucas-oliveirah17) | [LinkedIn](https://www.linkedin.com/in/lucas-oliveirah17/)

- **Daniel Navarro Porto** - Desenvolvedor Front-end & Segurança
   - [GitHub](https://github.com/danielnporto) | [LinkedIn](https://www.linkedin.com/in/danielnporto/)
 
---

## 📄 Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
