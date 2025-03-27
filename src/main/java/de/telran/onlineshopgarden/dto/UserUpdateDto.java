package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotBlank(message = "{validation.user.nameNotBlank}")
    @Size(min = 2, max = 50, message = "{validation.user.nameSize}")
    private String name;

    @NotBlank(message = "{validation.user.phoneNotBlank}")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "{validation.user.phone}")
    private String phone;
}