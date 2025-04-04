package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for carrying user login credentials.
 * <p>
 * This class is used to transfer data for user login operations, containing the necessary
 * credentials such as username (login) and password.
 * </p>
 *
 * @Setter               - Lombok annotation to generate setters for all fields.
 * @Getter               - Lombok annotation to generate getters for all fields.
 *
 * @author A-R
 * @version 1.0
 * @since 1.0
 */
@Setter
@Getter
public class JwtRequest {

    @NotBlank(message = "{validation.user.emailNotBlank}")
    @Email(regexp = "^[a-zA-Z][\\w.-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{validation.user.email}")
    private String login;

    @NotBlank(message = "{validation.user.passwordNotBlank}")
    @Size(min = 8, message = "{validation.user.passwordSize}")
    private String password;

}