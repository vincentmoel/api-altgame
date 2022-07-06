package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Entity.VwProductEntity;
import com.AltGame.AltGame.Repository.ProductRepo;
import com.AltGame.AltGame.Repository.VwProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserService userService;
    @Autowired
    VwProductRepo vwProductRepo;

    public List<VwProductEntity> index() {
        return vwProductRepo.findAllByStatus("active");
    }

    public List<VwProductEntity> searchByName(String search) {
        return vwProductRepo.findByStatusAndNameLike("active", "%" + search + "%");
    }

    public List<VwProductEntity> showUserProducts(String username) {
        return vwProductRepo.findByStatusAndUsername("active", username);
    }

    public List<VwProductEntity> show(int productId) {
        return vwProductRepo.findByProductId(productId);
    }

    public void store(ProductDto pDto, String username) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ProductEntity pEntity = new ProductEntity();
        int userId = userService.getUserIdByUsername(username);
        pEntity.setUserId(userId);
        pEntity.setImage(pDto.getImage().getBytes());
        pEntity.setProductId(pDto.getProductId());
        pEntity.setName(pDto.getName());
        pEntity.setCategoryId(pDto.getCategoryId());
        pEntity.setPrice(pDto.getPrice());
        pEntity.setDescription(pDto.getDescription());
        pEntity.setStatus("active");
        pEntity.setCreatedAt(timestamp);
        pEntity.setUpdatedAt(timestamp);
        productRepo.save(pEntity);
    }

    public void update(String username, int productId, ProductDto pDto) throws IOException {
        int userId = userService.getUserIdByUsername(username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ProductEntity pEntity = productRepo.findByProductIdAndUserId(productId, userId);

        pEntity.setImage(pDto.getImage().getBytes());
        pEntity.setCategoryId(pDto.getCategoryId());
        pEntity.setName(pDto.getName());
        pEntity.setPrice(pDto.getPrice());
        pEntity.setDescription(pDto.getDescription());
        pEntity.setUpdatedAt(timestamp);
        productRepo.save(pEntity);
    }

    public boolean destroy(String username, int productId) {
        int userId = userService.getUserIdByUsername(username);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        ProductEntity product = productRepo.findByProductIdAndUserId(productId, userId);
        product.setDeletedAt(timestamp);
        productRepo.save(product);

        return true;
    }

    public boolean setProductStatus(Integer productId, String status) {
        ProductEntity product = productRepo.findByProductId(productId);
        product.setStatus(status);

        productRepo.save(product);

        return true;
    }
}
