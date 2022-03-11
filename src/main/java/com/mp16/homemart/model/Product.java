package com.mp16.homemart.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Harga produk tidak boleh kosong")
    private long price;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Jenis kategori tidak boleh kosong")
    private Category category;

    public Product(String name, String description, long price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
