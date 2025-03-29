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

## Technology Stack

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL or H2 (for local development)
- Maven
- Lombok
- MapStruct
- JUnit
- Swagger/OpenAPI (optional)

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

## Repository Structure

src/ ├── main/ │ ├── java/ │ │ └── de/telran/onlineshopgarden/ │ │ ├── controller/ │ │ ├── dto/ │ │ ├── entity/ │ │ ├── repository/ │ │ ├── service/ │ │ └── exception/ │ └── resources/ │ ├── application.properties │ └── data.sql (optional)


## How to Run

1. Clone the repository:
    ```
    git clone https://github.com/BiOksana/onlineShopGarden.git
    cd onlineShopGarden
    ```

2. Run the application:
    ```
    ./mvnw spring-boot:run
    ```

3. Access the service locally at:
    ```
    http://localhost:8080
    ```

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

## License

This project is licensed under the MIT License.
