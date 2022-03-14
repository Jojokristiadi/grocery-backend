package com.mp16.homemart.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CategoryDTO {
    @NotBlank(message = "Nama kategori tidak boleh kosong")
    private final String name;
    private final Long parent;
}
