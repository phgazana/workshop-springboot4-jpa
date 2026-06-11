# Workshop API

API REST de e-commerce desenvolvida com Spring Boot, seguindo o padrão de arquitetura em camadas (Resource → Service → Repository).

## Tecnologias

- Java 26
- Spring Boot 4.0.6
- Spring Data JPA / Hibernate
- H2 Database (perfil de teste)
- PostgreSQL (perfil de produção)
- Maven

## Modelo de Domínio

```
User ──< Order >──< OrderItem >── Product >──< Category
                     │
                   Payment
```

- **User** — cliente com nome, email, telefone e senha
- **Order** — pedido vinculado a um cliente, com data/hora e status
- **OrderItem** — item de pedido com quantidade e preço; chave composta `(Order, Product)`
- **Payment** — pagamento associado 1-para-1 a um pedido
- **Product** — produto com nome, descrição, preço, URL de imagem e categorias (N:N)
- **Category** — categoria de produtos
- **OrderStatus** — enum com os estados: `WAITING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED`, `CANCELED`

## Endpoints

### Users `/users`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/users` | Lista todos os usuários |
| GET | `/users/{id}` | Busca usuário por ID |
| POST | `/users` | Cria novo usuário |
| PUT | `/users/{id}` | Atualiza nome, email e telefone |
| DELETE | `/users/{id}` | Remove usuário |

### Orders `/orders`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/orders` | Lista todos os pedidos |
| GET | `/orders/{id}` | Busca pedido por ID |

### Products `/products`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/products` | Lista todos os produtos |
| GET | `/products/{id}` | Busca produto por ID |

### Categories `/categories`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/categories` | Lista todas as categorias |
| GET | `/categories/{id}` | Busca categoria por ID |

## Tratamento de Erros

Erros são retornados no formato padronizado:

```json
{
  "timestamp": "2024-01-01T00:00:00Z",
  "status": 404,
  "error": "Resource not found",
  "message": "Resource id not found: 99",
  "path": "/users/99"
}
```

| Exceção | Status HTTP |
|---------|-------------|
| `ResourceNotFoundException` | 404 Not Found |
| `DatabaseException` | 400 Bad Request |

## Como executar

### Pré-requisitos

- Java 26+
- Maven 3.x

### Rodando em modo de teste (H2)

O perfil `test` já é o padrão. Basta executar:

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.  
O console do H2 fica disponível em `http://localhost:8080/h2-console` com as configurações:

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(vazio)* |

### Rodando com PostgreSQL

1. Crie um banco de dados PostgreSQL.
2. Configure as variáveis de ambiente (ou `application-dev.properties`):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```
3. Ative o perfil:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```

## Estrutura do Projeto

```
src/main/java/br/com/gazana/course/
├── config/
│   └── TestConfig.java          # Seed de dados para o perfil test
├── entities/
│   ├── enums/
│   │   └── OrderStatus.java
│   ├── pk/
│   │   └── OrderItemPK.java     # Chave composta de OrderItem
│   ├── Category.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Payment.java
│   ├── Product.java
│   └── User.java
├── repositories/                # Interfaces JPA
├── resources/                   # Controllers REST
│   └── exceptions/              # Handler global de erros
├── services/                    # Regras de negócio
│   └── exceptions/              # Exceções de serviço
└── CourseApplication.java
```
