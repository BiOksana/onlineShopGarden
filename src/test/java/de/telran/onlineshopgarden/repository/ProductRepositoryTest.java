package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Category;
import de.telran.onlineshopgarden.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false"
})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void mustFindProductWithHighestDiscount() {
        Category category = new Category();
        category.setName("Test category");
        category.setImageUrl("https://example.com/category.png");
        entityManager.persist(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(new BigDecimal("100"));
        product1.setDiscountPrice(new BigDecimal("70"));
        product1.setImageUrl("https://example.com/img1.jpg");
        product1.setDescription("Test product 1");
        product1.setCategory(category);
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(new BigDecimal("100"));
        product2.setDiscountPrice(new BigDecimal("80"));
        product2.setImageUrl("https://example.com/img2.jpg");
        product2.setDescription("Test product 2");
        product2.setCategory(category);
        entityManager.persist(product2);

        Optional<Product> result = productRepository.findProductWithHighestDiscount();

        assertTrue(result.isPresent());
        assertEquals("Product 1", result.get().getName());
    }

}
