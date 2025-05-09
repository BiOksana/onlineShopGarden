package de.telran.onlineshopgarden.controller.api;

import de.telran.onlineshopgarden.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Favorite Controller", description = "REST API for managing favorites in the app")
public interface FavoriteControllerApi {

    @Operation(summary = "Get user's favorite products for the currently authenticated user",
            description = "Retrieves a list of favorite products of the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<List<ProductDto>> getUserFavorites();

    @Operation(summary = "Add product to favorites for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<Void> addToFavorites(@RequestParam Long productId);

    @Operation(summary = "Remove product from favorites for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<Void> removeFromFavorites(@RequestParam Long productId);
}