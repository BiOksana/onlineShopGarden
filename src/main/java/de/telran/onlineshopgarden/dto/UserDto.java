package de.telran.onlineshopgarden.dto;

import de.telran.onlineshopgarden.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;

    private String name;

    private String email;

    private String phone;

    private Role role;
}