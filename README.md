### Sobre

A aplicação foi construída com:
- Java 17
- Spring Boot 3.1.1 
- Maven
- H2 Database
- Lombok

A aplicação roda na porta 8080. Ao ser executada é disponibilizada uma API REST, onde sua documentação pode ser visualizada em seu Swagger na URL http://{host}:{porta}/swagger-ui/index.html#/.

Por padrão o projeto utiliza um banco de dados relacional em memória para persitir os dados.

### Requisitos
Para executar a aplicação é necessário conter:
- Java 17
- Maven

### Execução

1. Clone o repositório
2. Acesse o diretório do projeto clonado
3. Compile e gere o pacote da aplicação:

`mvn clean package`

4. Se deseja executar a aplicação através do Java, use:

`java -jar target/management-0.0.1-SNAPSHOT.jar`

5. Se desejar executar a aplicação em um container Docker, use:

`docker-compose up`