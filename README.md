# :sos: Projeto: S.O.S Cidadão

## :bulb: Visão Geral

O a API do *S.O.S Cidadão* é uma solução desenvolvida em *Java com Spring Boot* que visa oferecer suporte ágil e eficaz a pessoas afetadas por desastres naturais, como enchentes, deslizamentos e incêndios. A aplicação proporciona uma *API REST completa* que permite o gerenciamento de usuários, abrigos, voluntários e pessoas resgatadas, promovendo a conexão entre quem precisa de ajuda e quem pode oferecer apoio.

A proposta do sistema busca mitigar os impactos de eventos extremos utilizando tecnologia, inovação e boas práticas de desenvolvimento orientadas ao back-end.

---

## :white_check_mark: Funcionalidades Implementadas

* Cadastro e autenticação de usuários por meio de token JWT
* Classificação de usuários como Abrigado e Voluntário.
* Registro e gerenciamento de abrigos e pessoas resgatadas
* Integração de localização por endereço
* Paginação, ordenação e filtros nas listagens
* Validações com Bean Validation
* API documentada com Swagger UI
* Código organizado seguindo boas práticas de arquitetura em camadas
* Deploy em ambiente Docker e pronto para nuvem

---

## :star: Tecnologias Utilizadas

* *Linguagem:* Java 17+
* *Framework:* Spring Boot
* *Banco de Dados:* PostgreSQL
* *ORM:* Spring Data JPA
* *Segurança:* JWT Token + Spring Security
* *Validação:* Bean Validation
* *Documentação:* Swagger/OpenAPI
* *Gerenciador de Dependências:* Maven
* *Containerização:* Docker + Docker Compose

---


## :test_tube: Testes e Demonstração

1. Link do Deploy Online no Render:
* :link: [sos-cidadao-java.onrender.com](https://sos-cidadao-java.onrender.com)

2. Com Docker localmente:
- Para rodar dessa maneira é necessário ter o Docker instalado: [docker.com](https://www.docker.com/get-started/)
```bash
docker compose up --build
```
3. Depois de rodar o projeto o Swagger estará disponível no link abaixo:

- :link: http://localhost:8080/swagger-ui.html

## Vídeos

* :video_camera: Vídeo com demonstração - DevOps: [Demonstração com Uso do Docker](https://youtu.be/o2Nn54idX6E)
* :video_camera: Vídeo com demonstração completa: [Demonstração Completa da Solução](https://youtu.be/0XoJcfAWtDU)
* :microphone: Pitch explicativo: [Pitch SOS Cidadão](https://www.youtube.com/watch?v=yCv61KhzksM)

---