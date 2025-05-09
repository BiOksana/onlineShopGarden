package de.telran.onlineshopgarden.controller.api;

import de.telran.onlineshopgarden.dto.OrderCreateDto;
import de.telran.onlineshopgarden.dto.OrderDto;
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

@Tag(name = "Order Controller", description = "REST API for managing orders in the app")
public interface OrderControllerApi {

    @Operation(summary = "Admin only - Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<List<OrderDto>> getAll();

    @Operation(summary = "Authorized users (Client or Admin) — Get order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<OrderDto> getById(
            @Parameter(description = "The ID of the order to retrieve", required = true)
            @PathVariable Long orderId);

    @Operation(summary = "Get order history by user id for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<List<OrderDto>> getOrderHistory();

    @Operation(summary = "Create new order for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<OrderDto> create(@Valid @RequestBody OrderCreateDto orderCreateDto);

    @Operation(summary = "Cancel order for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<Void> cancelOrder(
            @Parameter(description = "The ID of the order to cancel", required = true)
            @PathVariable Long orderId);
}
