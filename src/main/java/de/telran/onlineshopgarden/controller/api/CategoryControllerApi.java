package de.telran.onlineshopgarden.controller.api;

import de.telran.onlineshopgarden.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Category Controller", description = "REST API for managing category in the app")
public interface CategoryControllerApi {

    @Operation(summary = "Public endpoint - Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories successfully retrieved")
    })
    ResponseEntity<List<CategoryDto>> getAll();

    @Operation(summary = "Public endpoint - Get category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    ResponseEntity<CategoryDto> getById(
            @Parameter(description = "The ID of the category to retrieve", required = true)
            @PathVariable Long categoryId);

    @Operation(summary = "Admin only - Create new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully created"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
    })
    ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto dto);

    @Operation(summary = "Admin only - Update category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<CategoryDto> update(
            @Parameter(description = "The ID of the category to update", required = true)
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryDto dto);

    @Operation(summary = "Admin only - Delete category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "The ID of the category to delete", required = true)
            @PathVariable Long categoryId);
}
