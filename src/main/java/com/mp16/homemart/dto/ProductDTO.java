package com.mp16.homemart.dto;


import com.mp16.homemart.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDTO {
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String name;
    private String description;
    @NotNull(message = "Harga produk tidak boleh kosong")
    private long price;
    @NotNull(message = "Jenis kategori tidak boleh kosong")
    private Category category;
}
