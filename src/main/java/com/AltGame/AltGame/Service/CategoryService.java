package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Entity.CategoryEntity;
import com.AltGame.AltGame.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public List<CategoryEntity> index(){
        return categoryRepo.findAll();
    }

    public boolean exitsByCategoryId(Integer id){
        return !categoryRepo.existsById(id);
    }

    public String getCategory(int categoryId) {
        CategoryEntity categoryEntity = categoryRepo.getById(categoryId);
        return categoryEntity.getName();
    }
}
