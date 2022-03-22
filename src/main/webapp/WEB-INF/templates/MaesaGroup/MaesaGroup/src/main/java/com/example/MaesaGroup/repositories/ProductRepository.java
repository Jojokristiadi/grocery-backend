package com.example.MaesaGroup.repositories;

import com.example.MaesaGroup.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
