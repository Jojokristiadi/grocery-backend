package com.mp16.homemart.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Nama kategori tidak boleh kosong")
    private String name;

    @Column
    private Long parent;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Collection<Product> products;

    public Category(String name, Long parent) {
        this.name = name;
        this.parent = parent;
    }
}
