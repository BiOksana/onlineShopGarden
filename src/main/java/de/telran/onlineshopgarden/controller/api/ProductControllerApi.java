package de.telran.onlineshopgarden.controller.api;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.dto.ProductsFilterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "Product Controller", description = "REST API for managing products in the app")
@RestController
@RequestMapping("/products")
@Validated
public interface ProductControllerApi {

    @Operation(summary = "Public endpoint - Retrieve paginated list of products",
            description = """
                    Returns a paginated list of products with optional filtering.<br>
                    Filter parameters are provided in the request body (all parameters are optional).<br>
                    This allows filtering products by various criteria (category, price range, discount price).<br>
                    If no filter parameters are supplied, the endpoint returns all products,
                    sorted by name and limited to the first page using the default page size of 5.
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully retrieved")
    })
    @GetMapping
    Page<ProductDto> getProducts(
            @ParameterObject ProductsFilterRequest filterRequest,
            @ParameterObject @PageableDefault(size = 5, sort = "name") Pageable pageable
    );

    @Operation(summary = "Public endpoint - Get product by id",
            description = "Returns detailed information about a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ResponseEntity<ProductDto> getById(
            @Parameter(description = "The ID of the product to retrieve", required = true)
            @PathVariable Integer productId);

    @Operation(summary = "Public endpoint - Get product with highest discount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No discounted products found")
    })
    @GetMapping("/productOfTheDay")
    ResponseEntity<ProductDto> getProductOfTheDay();

    @Operation(summary = "Admin only - Create new product",
            description = "Accessible only to users with the ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto);

    @Operation(summary = "Admin only - Update product by id",
            description = "Accessible only to users with the ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<ProductDto> update(
            @Parameter(description = "The ID of the product to update", required = true)
            @PathVariable Integer productId,
            @Valid @RequestBody ProductDto dto);

    @Operation(summary = "Admin only - Set discount price for product",
            description = "Accessible only to users with the ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<ProductDto> setDiscountPrice(
            @Parameter(description = "The ID of the product to update discount price", required = true)
            @PathVariable Integer productId,
            @DecimalMin(value = "0.01", message = "{validation.product.discountPrice}")
            @RequestParam(required = false) BigDecimal discountPrice);

    @Operation(summary = "Admin only - Delete product by id",
            description = "Accessible only to users with the ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "The ID of the product to delete", required = true)
            @PathVariable Integer productId);
}