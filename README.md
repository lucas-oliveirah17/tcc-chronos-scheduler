# Sistema de Agendamento para Barbearias - TCC

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring_Boot-3.5.7-green?logo=spring)
![React](https://img.shields.io/badge/React-19.2-blue?logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)
![License](https://img.shields.io/badge/License-AGPL_v3-blue.svg?style=flat-square)

> Aplicação web para otimizar a gestão de agendamentos em barbearias. Substitui o controle manual por um painel de admin (Java/Spring) e autoatendimento ao cliente (React). Projeto de TCC do curso de Análise e Desenvolvimento de Sistema (IFSP - Guarulhos).

**🚀 Status do Projeto:** Em Desenvolvimento

---

## 📚 Índice

* [Sobre o Projeto](#-sobre-o-projeto)
* [Funcionalidades Principais](#-funcionalidades-principais)
* [Arquitetura](#-arquitetura)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Como Executar o Projeto (Ambiente Local)](#%EF%B8%8F-como-executar-o-projeto-ambiente-local)
* [Autores](#-autores)
* [Licença](#-licença)

---

## 📖 Sobre o Projeto

Este projeto é o Trabalho de Conclusão de Curso (TCC) do curso de **Análise e Desenvolvimento de Sistemas do IFSP - Campus Guarulhos**.

O objetivo é desenvolver uma solução de software moderna (uma Single Page Application) para sanar as ineficiências do agendamento manual em barbearias, oferecendo uma plataforma digital que centraliza a gestão para o administrador e proporciona conveniência para o cliente.

---

## 📊 Apresentação do Projeto

[![Visualizar Apresentação](https://img.shields.io/badge/Visualizar-Apresentação-b31b1b?style=for-the-badge&logo=adobe-acrobat-reader&logoColor=white)](docs/Chronos_TCC_Apresentacao.pdf)

Clique no botão acima para visualizar os slides da apresentação utilizada na banca.

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
    * Java 25 (LTS)
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

## 🛠️ Como Executar o Projeto (Ambiente Local)

O projeto é divido em `backend` (API em Java/Spring) e `frontend` (Aplicação em React/Vite).
Para facilitar o gerenciamento, incluímos o utilitário de automação `chronos`.

### 1. Pré-requisitos:
- [Git](https://git-scm.com/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (para o banco de dados)
- [JDK 25 (OpenJDK)](https://adoptium.net/))
- IDE Java ([Eclipse](https://www.eclipse.org/downloads/packages/release/2025-09/r/eclipse-ide-java-developers) ou [IntelliJ](https://www.jetbrains.com/idea/)) 

### 2. Clonar o Repositório

```bash
git clone https://github.com/lucas-oliveirah17/tcc-barber-scheduler.git
cd tcc-barber-scheduler
```

### 3. Configurar Variável de Ambiente

Copie o arquivo `.env.example`, e o renomeie para `.env`'. Configure as variáveis de ambiente como preferir.

```bash
cp .env.example .env # No Windows (CMD): copy .env.example .env
```

Dê também permissão de execução ao utilizatário (Linux/Git Bash)

```bash
chmod +x chronos
```

### 4. Execução com o Utilitário Chronos

O script `./chronos` gerencia todo o ecossistema Docker do projeto. Escolha um dos modos abaixo:

#### A) Modo Demonstração:

Sobe todos os serviços (Banco, Back e Front) no Docker. Ideal para ver o projeto funcionando rapidamente sem abrir IDEs.

```bash
./chronos start
```

#### B) Modo Desenvolvedor:

Sobe apenas o Banco de Dados, pgAdmin e o Frontend no Docker. O Backend fica livre para ser executado na sua IDE, permitindo Debug e Hot Swap.

```bash
./chronos dev
```

#### Endereços Locais:
| Serviço | URL |
| :--- | :--- |
| **Frontend** | [http://localhost:5173](http://localhost:5173) |
| **Backend** | [http://localhost:8080/api](http://localhost:8080/api) |
| **pgAdmin** | [http://localhost:5050](http://localhost:5050) |


### 5. Configuração para Execução via IDE

Ao utilizar o comando `./chronos dev`, o utilitário gerencia automaticamente a infraestrutura e a configuração do ambiente:

#### A) Sincronização Automática

O utilitário gera automaticamente o arquivo `backend/src/main/resources/application-local.properties` baseando-se nos valores definidos no seu `.env` da raiz.

> **Nota:** Os arquivos `.example` presentes no repositório servem apenas como referência técnica da estrutura necessária. O uso do utilitário dispensa a configuração manual destes arquivos.

#### B) Rodar o Back-end via Eclipse (Java/Spring):

1. Importe a pasta `backend` como no **Existing Maven Project**.
2. Aguarde o Maven baixar as dependências.
3. Execute a classe `BackendApplication.java` como **Java Application**.

### 6. Comandos Úteis do `chronos`

| Atalho | Comando Completo | Descrição |
| :--- | :--- | :--- |
| `-s` | `start` | Inicia o ecossistema completo no Docker |
| `-d` | `dev` | Modo IDE: Sobe infra e libera a porta 8080 |
| `-p` | `stop` | Para os containers (mantém os dados) |
| `-r` | `restart` | Recompila e reinicia apenas o Backend |
| `-st` | `status` | Lista containers ativos e portas |
| `-l` | `logs` | Exibe logs em tempo real |
| `-db` | `database` | Abre o terminal SQL (psql) do Postgres |
| `-rb` | `rebuild` | Força reconstrução das imagens sem cache |
| `-c` | `clean` | **Reset Total:** Remove containers e volumes |
| `-h` | `help` | Mostra o menu de ajuda com todos os comandos |

### 7. Dica de Produtividade: Atalho chronos
Para evitar digitar `./` todas as vezes, o projeto já inclui um arquivo `.bashrc` na raiz com aliases pré-configurados.

#### A) Ativar Temporariamente (Apenas na sessão atual):

Sempre que abrir o terminal na pasta do projeto, você pode carregar os atalhos rapidamente:

```bash
source .bashrc
```

#### B) Configuração Permanente:

Se você utiliza **Git Bash** ou **Linux** e deseja usar o comando `chronos` de qualquer lugar do terminal sem precisar do `./`, execute o comando abaixo **dentro da pasta raiz do projeto**:

```bash
echo "alias chronos='bash $PWD/chronos'" >> ~/.bashrc && source ~/.bashrc
```

Após rodar este comando, você poderá digitar apenas `chronos start` ou `chronos help` em qualquer nova janela do terminal.

## 🎓 Autores
- **Lucas Silva de Oliveira** - Desenvolvedor Back-end & Segurança
   
   [![GitHub](https://img.shields.io/badge/GitHub-100000?style=plastic&logo=github&logoColor=white)](https://github.com/lucas-oliveirah17)
   [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=plastic&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/lucas-oliveirah17/)

- **Daniel Navarro Porto** - Desenvolvedor Front-end 

   [![GitHub](https://img.shields.io/badge/GitHub-100000?style=plastic&logo=github&logoColor=white)](https://github.com/danielnporto)
   [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=plastic&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/danielnporto/)

---

## 📚 Documentação e Links Úteis

Aqui estão alguns recursos adicionais para entender melhor o projeto:

* [**Relatório Técnico**](https://www.overleaf.com/read/mqkgkrjsndqn#8f4c2a)
* [**Modelagem do Banco de Dados (DER)**](docs/diagramas/Entidade-Relacionamento.png)
* [**Casos de Uso**](docs/diagramas/Casos_de_Uso.png)
* [**Diagrama de Sequência - Autenticação do Usuário**](docs/diagramas/Sequencia_RF05_Autenticacao_Usuario.png)
* [**Diagrama de Sequência - Criar Agendamento**](docs/diagramas/Sequencia_RF10_Criar_Agendamento.png)

---

## 📄 Licença

Este projeto está licenciado sob a **GNU Affero General Public License v3.0 (AGPL-3.0)**. 

Os autores originais (**Lucas Oliveira** e **Daniel Porto**) reservam-se o direito de relicenciar este software para fins comerciais ou versões proprietárias. A licença AGPL garante que qualquer derivado distribuído ou oferecido via rede permaneça com o código fonte aberto sob os mesmos termos. Veja o arquivo [LICENSE](LICENSE) para detalhes.
