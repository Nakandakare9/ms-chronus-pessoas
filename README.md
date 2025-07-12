# 👥 CHRONUS - Microsserviço de Cadastro de Pacientes

Este repositório contém o microsserviço `ms-chronus-pessoas`, responsável por gerir os dados dos **pacientes**, seus **contatos de apoio (Anjos)** e seus **profissionais de saúde vinculados**. Faz parte do ecossistema do sistema CHRONUS, uma solução inovadora voltada ao acompanhamento automatizado de pacientes crônicos no SUS.

---

## 📌 Funcionalidades

- Cadastro, atualização e consulta de:
  - Pacientes com dados clínicos e de contato
  - Contatos de apoio ("Anjos") relacionados a cada paciente
  - Profissionais de saúde vinculados a pacientes
- Validação de CPF e e-mail (campos únicos)
- Relacionamentos muitos-para-muitos entre pacientes, anjos e profissionais

---

## 🏗️ Arquitetura

A estrutura do projeto segue os princípios da **Arquitetura Limpa (Clean Architecture)**, com separação entre regras de negócio, lógica de aplicação, camadas técnicas e controladores REST.

- `domain`: modelos e entidades do domínio
- `application`: serviços, DTOs e mapeamentos
- `infrastructure`: persistência JPA e repositórios
- `web`: controllers e handlers de exceções

> Projeto desenvolvido com Java 17, Spring Boot 3 e JPA. Banco de dados H2 utilizado no ambiente de desenvolvimento.

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17
- Maven 3.8+

### Execução local

```bash
git clone https://github.com/Nakandakare9/ms-chronus-pessoas.git
cd ms-chronus-pessoas
mvn clean spring-boot:run
```

### Acesso ao Console H2

- URL: `http://localhost:8080/h2-console`
- JDBC: `jdbc:h2:mem:testdb`
- Usuário: `sa` | Senha: (em branco)

---

## 🔍 Documentação da API

- Acesse: `http://localhost:8080/swagger-ui.html`

---

## ✅ Testes e Qualidade

- Testes com JUnit 5 + Mockito
- Cobertura mínima de 80% garantida com Jacoco

Para gerar os testes:

```bash
mvn clean test
mvn jacoco:report
```

> Relatório em: `target/site/jacoco/index.html`

---

## 🛠️ Melhorias Futuras

- Validações via CPF externo (cadastro único)
- Integração com um serviço de autenticação (OAuth2)
- Conformidade com LGPD na persistência de dados sensíveis

---

## 🤝 Contribuidores

- [@Nakandakare9](https://github.com/Nakandakare9)
- [@CamilaLima21](https://github.com/CamilaLima21)

---

## 📄 Licença

Este projeto está licenciado sob a Licença MIT.
