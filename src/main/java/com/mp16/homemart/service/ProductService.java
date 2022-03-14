package com.mp16.homemart.service;

import com.mp16.homemart.model.Category;
import com.mp16.homemart.model.Product;
import com.mp16.homemart.repository.CategoryRepository;
import com.mp16.homemart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    List<Product> getAllProduct;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public Product get(long id) {
        return productRepository.findById(id).get();
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
