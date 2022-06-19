package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public List<ProductEntity> index(){
        return productRepo.findAll();
    }
}
