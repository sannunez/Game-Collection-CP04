# CheckPoint 04 - Domain Driven Design

Repositório dedicado ao **CheckPoint 04** da disciplina **Domain Driven Design**, ministrada por **Gustavo Molina** na **FIAP - Unidade Paulista**.

---

## Sobre o Projeto

Este projeto consiste em uma **interface Java** desenvolvida com a biblioteca **Swing**, que permite a interação com um banco de dados SQL através de **JDBC**. O sistema implementa operações de **CRUD** (Create, Read, Update e Delete), oferecendo funcionalidades completas de gerenciamento de dados.

O objetivo do exercício é aplicar os conceitos de **Domain Driven Design (DDD)**, estruturando o código em camadas coerentes e promovendo uma arquitetura clara e organizada.

---

## Funcionalidades

- **Cadastro de registros:** Inserção de novos dados na aplicação.  
- **Listagem de registros:** Visualização de todos os dados armazenados em uma tabela.  
- **Atualização de registros:** Alteração de informações existentes.  
- **Exclusão de registros:** Remoção de dados selecionados.  
- **Interface gráfica interativa:** Desenvolvida com **Java Swing** para facilitar a experiência do usuário.  

---

## Tecnologias Utilizadas

- **Java 11+**  
- **Swing** para interface gráfica  
- **JDBC** para conexão com o banco de dados  
- **SQL** para persistência e manipulação de dados  

---

## Estrutura do Projeto

O projeto está organizado de acordo com práticas MVC, incluindo camadas separadas para:

- **Model:** Representação das entidades do domínio
- **DB:** Comunicação com Banco de Dados.  
- **DAO:** Classes responsáveis pelo acesso e manipulação do banco de dados  
- **Controller / Functions:** Lógica de manipulação de dados e operações CRUD  
- **View:** Interfaces gráficas para interação do usuário  

---

## Como Executar
git clone <URL_DO_REPOSITORIO>

## Como Executar

1. Abra o projeto em sua IDE favorita (Eclipse, IntelliJ, NetBeans, etc).  
2. Configure a conexão JDBC com seu banco de dados SQL (lembre-se de criar uma database local e inserir os dados relativos a ela, este repositório ja possui .jar compatível com MySQL).
3. Compile e execute a classe principal:  
```java
galeriaDeJogosGUI.java

