package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Category;
import de.telran.onlineshopgarden.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findProductWithHighestDiscountMustReturnProductFromLiquibase() {
        Optional<Product> optional = productRepository.findProductWithHighestDiscount();

        assertTrue(optional.isPresent(), "Product with discount must be found");
        assertEquals("Shovel", optional.get().getName());
    }

    @Test
    void findProductWithHighestDiscountMustReturnNewProductWithMaxDiscount() {
        Category category = categoryRepository.findById(1L).orElseThrow();

        Product newProduct = new Product();
        newProduct.setName("Super Discount Product");
        newProduct.setPrice(BigDecimal.valueOf(100));
        newProduct.setDiscountPrice(BigDecimal.valueOf(30));
        newProduct.setCategory(category);
        newProduct.setImageUrl("https://example.com/img_super_discount.jpg");
        newProduct.setDescription("Test super discounted product");

        productRepository.save(newProduct);

        Optional<Product> optional = productRepository.findProductWithHighestDiscount();

        assertTrue(optional.isPresent());
        assertEquals("Super Discount Product", optional.get().getName(),
                "Newly added product with highest discount must be returned");
    }

    @Test
    void existsByCategoryIdMustReturnTrueForExistingCategory() {
        boolean exists = productRepository.existsByCategoryId(1L);
        assertTrue(exists, "Category with id 1 must exist");
    }
}
