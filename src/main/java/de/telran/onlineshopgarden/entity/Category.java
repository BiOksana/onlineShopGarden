package de.telran.onlineshopgarden.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer categoryId;

    @NotBlank(message = "Category name must not be empty")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "URL image must not be empty")
    @Pattern(regexp = "^(https?://)?([\\w\\d.-]+)\\.([a-z]{2,6}\\.?)([/\\w\\d.-]*)*/?$",
            message = "Invalid URL image format")
    private String imageUrl;

}
