package de.telran.onlineshopgarden.controller.api;

import de.telran.onlineshopgarden.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "User Controller", description = "REST API for managing users in the app")
public interface UserControllerApi {

    @Operation(
            summary = "Admin only - Retrieve all users",
            description = "Returns a list of all registered users. Accessible only by users with the ROLE_ADMINISTRATOR authority.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<List<UserDto>> getAll();

    @Operation(
            summary = "Admin only - Get user by ID",
            description = "Retrieves a specific user based on the provided user ID. Accessible only by administrators (ROLE_ADMINISTRATOR)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    ResponseEntity<UserDto> getById(
            @Parameter(description = "The ID of the user to retrieve", required = true)
            @PathVariable Long userId);

    @Operation(
            summary = "Public endpoint - Register a new user",
            description = """
                    Creates a new user using the provided registration details. Returns the created user data.<br>
                    Validation constraints are applied on all fields in the request body.
                     """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto userCreateDto);

    @Operation(
            summary = "Public endpoint - Authenticate user (Login)",
            description = "Authenticates a user using the provided credentials and returns a JWT token if successful.<br>" +
                    "The request body should include user credentials."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    })
    ResponseEntity<JwtResponse> login(
            @Parameter(description = "User credentials for authentication", required = true)
            @Valid @RequestBody JwtRequest jwtRequest) throws AuthException;

    @Operation(
            summary = "Authorized users (Client or Admin) — Update user by ID",
            description = """
                    Updates the name and phone number of a user by their ID.

                    - Accessible by:
                      - the user themselves (ROLE_CLIENT)
                      - administrators (ROLE_ADMINISTRATOR)
                    - Validates input fields.
                    - Returns 403 if a client tries to update another user's data.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<UserDto> update(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateDto dto);


    @Operation(
            summary = "Authorized users (Client or Admin) — Delete user by ID",
            description = """
                    Deletes (anonymizes) user data by their ID.

                    - Accessible by:
                      - the user themselves (ROLE_CLIENT)
                      - administrators (ROLE_ADMINISTRATOR)
                    Deactivates the user account and removes access to personal data.
                    Clients can delete only their own account. Admins can delete any user and other admins.
                    Also removes related data such as favorites and cart contents.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<Void> mask(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long userId);
}