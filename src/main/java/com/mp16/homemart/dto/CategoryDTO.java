package com.mp16.homemart.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CategoryDTO {
    @NotBlank(message = "Masukan nama kategori")
    private final String name;
    private final Long parent;
}
