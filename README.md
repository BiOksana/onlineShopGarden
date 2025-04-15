# Online Shop Garden

**Online Shop Garden** is a web-based e-commerce application for selling gardening products.  
It is developed in Java using the Spring Boot framework and follows a layered, modular architecture with RESTful APIs and database integration.  
Aspect-Oriented Programming (AOP) is used for logging and security cross-cutting concerns.

## Deployed Application

The deployed version of the application is available at:  
🔗 https://onlineshopgarden-production.up.railway.app/swagger-ui/index.html

For local development, Swagger UI documentation is accessible at:  
🔗 http://localhost:8080/swagger-ui/index.html

## Technology Stack

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security
- Aspect-Oriented Programming (AOP)
- Tomcat
- Log4j2
- MySQL, Liquibase
- Maven
- Lombok
- MapStruct
- JUnit 5, Mockito
- Swagger
- Docker
- JWT Authentication

## Domain Model

- `Product`: Includes `id`, `name`, `price`, `discountPrice`, `description`, `image`, `categoryId`
- `Category`: Defines product categorization
- `User`: Represents a registered customer
- `Cart` and `CartItem`: Contain selected products before ordering
- `Order` and `OrderItem`: Define placed orders and their items
- `Favourite`: Contains products marked as favorite by users

## Environment Variables

Please ensure the following environment variables are set when running the application locally or with Docker:

```
DB_URL=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET_ACCESS=
JWT_SECRET_REFRESH=
```

## Development Team

- **Oksana Bibikova** (Team Lead)
- **Yevhen Kariev**
- **Vladimir Sheftel**

## License

This project is licensed under the MIT License.
