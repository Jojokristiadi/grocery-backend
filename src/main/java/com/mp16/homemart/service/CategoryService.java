package com.mp16.homemart.service;

import com.mp16.homemart.model.Category;
import com.mp16.homemart.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    List<Category> getAllCategory;

    public List<Category> getGetAllCategory() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category get(long id) {
        return categoryRepository.findById(id).get();
    }

    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
