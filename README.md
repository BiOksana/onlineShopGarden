# Online Shop Garden

**Online Shop Garden** is a web-based e-commerce application for selling gardening products.  
It is developed in Java using the Spring Boot framework and follows a layered, modular architecture with RESTful APIs and database integration.

## Features

- Product browsing with optional category filtering
- Shopping cart management
- Order creation and cancellation
- Favorites functionality for authenticated users
- “Product of the Day” feature based on the highest absolute discount (price minus discount price)
- Extendable administrative functionality

[Schema image, db scripts and docs](https://github.com/BiOksana/onlineShopGarden/tree/master/docs)

## Technology Stack

- Java 17+
- Spring Boot: Web, Data
- Spring Data JPA (Hibernate)
- Spring Security
- Tomcat
- Log4j2
- MySQL, liquibase
- Maven
- Lombok
- MapStruct
- JUnit 5, Mockito
- Swagger

## Domain Model

- `Product`: Includes `id`, `name`, `price`, `discountPrice`, `description`, `image`, `categoryId`
- `Category`: Defines product categorization
- `User`: Represents a registered customer
- `Cart` and `CartItem`: Contain selected products before ordering
- `Order` and `OrderItem`: Define placed orders and their items
- `Favourite`: Contains products marked as favorite by users

## Discount Logic

Discounts are defined by the `discountPrice` field only.  
The `price` field holds the full price.  
Percentage discounts are not stored.  
The "Product of the Day" is the product with the greatest price reduction (`price - discountPrice`).  
In case of multiple products with the same maximum discount, one is selected randomly.


## How to Run

1. Setup MySQL DB. Create database and set configs in application.properties file
2. Run the application:
   mvn clean install spring-boot:start
3. Access the service locally at:
   https://onlineshopgarden-production.up.railway.app/swagger-ui/index.html#/
4. USING Docker

   - Create Docker file:
   
   
     FROM eclipse-temurin:17-alpine AS builder

     WORKDIR /app

     COPY pom.xml ./
     COPY .mvn .mvn
     COPY mvnw ./

     RUN chmod +x mvnw && ./mvnw dependency:go-offline

     COPY src ./src

     RUN ./mvnw package -DskipTests

     FROM eclipse-temurin:17-alpine
     WORKDIR /app
     COPY --from=builder /app/target/onlineShopGarden-0.0.1-SNAPSHOT.jar app.jar
     ENTRYPOINT ["java", "-jar", "app.jar"]
   - Create Docker-compose.yml

   
     version: '3.8'

     services:
     mysql:
     image: mysql:8.0
     container_name: mysql-container
     restart: always
     environment:
     MYSQL_ROOT_PASSWORD: root
     MYSQL_DATABASE: gardenapp
     networks:
     - spring-net
     healthcheck:
     test: ["CMD", "mysqladmin", "ping", "-h", "mysql"]
     interval: 10s
     timeout: 5s
     retries: 5

     springboot:
     build: .
     container_name: springboot-container
     depends_on:
     mysql:
     condition: service_healthy
     ports:
     - "8080:8080"
     environment:
     - SPRING_PROFILES_ACTIVE=docker
     - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/gardenapp
     - SPRING_DATASOURCE_USERNAME=root
     - SPRING_DATASOURCE_PASSWORD=root
     networks:
     - spring-net

     networks:
     spring-net:

## Sample Endpoints

| HTTP Method | Endpoint                               | Description                                 |
|-------------|----------------------------------------|---------------------------------------------|
| GET         | `/api/products`                        | Retrieve all products                       |
| GET         | `/api/products/product-of-the-day`     | Retrieve the product with the highest discount |
| POST        | `/api/orders`                          | Create a new order                          |
| PATCH       | `/api/orders/{id}/cancel`              | Cancel an existing order (if allowed)       |
| GET         | `/api/favourites`                      | Retrieve favorite products                  |
| POST        | `/api/favourites/{productId}`          | Add a product to user's favorites           |

## Contribution Guidelines

1. Fork the repository
2. Create a feature branch
3. Make your changes and commit
4. Open a pull request with a clear description

Please follow clean code principles and include test coverage for new functionality.

## Software developers team
 - Oksana Bibikova (Team Lead)
 - Yevhen Kariev
 - Vladimir Sheftel
## License

This project is licensed under the MIT License.


