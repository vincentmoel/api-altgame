package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserService userService;

    public List<ProductEntity> index() {
        return productRepo.findAll();
    }

    public List<ProductEntity> showUserProducts(String username) {
        int userId = userService.getUserIdByUsername(username);
        return productRepo.findByUserId(userId);
    }

    public ProductEntity show(int productId) {
        return productRepo.findByProductId(productId);
    }

    public ProductEntity store(ProductDto pDto, String username) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ProductEntity pEntity = new ProductEntity();
        int userId = userService.getUserIdByUsername(username);

        pEntity.setUserId(userId);
        pEntity.setImage(pDto.getImage());
        pEntity.setProductId(pDto.getProductId());
        pEntity.setName(pDto.getName());
        pEntity.setCategoryId(pDto.getCategoryId());
        pEntity.setPrice(pDto.getPrice());
        pEntity.setDescription(pDto.getDescription());
        pEntity.setStatus(pDto.getStatus());
        pEntity.setCreatedAt(timestamp);
        pEntity.setUpdatedAt(timestamp);

        return productRepo.save(pEntity);
    }

    public ProductEntity update(String username, int productId, ProductDto pDto) {
        int userId = userService.getUserIdByUsername(username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ProductEntity pEntity = productRepo.findByProductIdAndUserId(productId, userId);

        pEntity.setImage(pDto.getImage());
        pEntity.setName(pDto.getName());
        pEntity.setPrice(pDto.getPrice());
        pEntity.setDescription(pDto.getDescription());
        pEntity.setStatus(pDto.getStatus());
        pEntity.setUpdatedAt(timestamp);

        return productRepo.save(pEntity);
    }

    public boolean destroy(String username, int productId) {
        int userId = userService.getUserIdByUsername(username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        ProductEntity product = productRepo.findByProductIdAndUserId(productId, userId);
        product.setDeletedAt(timestamp);

        productRepo.save(product);

        return true;
    }
}
