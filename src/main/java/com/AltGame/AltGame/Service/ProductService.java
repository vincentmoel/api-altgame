package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.ProductDto;
import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Entity.VwProductEntity;
import com.AltGame.AltGame.Repository.ProductRepo;
import com.AltGame.AltGame.Repository.VwProductRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserService userService;
    @Autowired
    VwProductRepo vwProductRepo;

    private final Cloudinary cloudinary;

    public List<VwProductEntity> index() {
        List<String> statuses = new ArrayList<>();
        statuses.add("active");
        statuses.add("bidded");
        return vwProductRepo.findAllByStatusIn(statuses);
    }

    public List<VwProductEntity> searchByName(String search) {
        List<String> statuses = new ArrayList<>();
        statuses.add("active");
        statuses.add("bidded");
        return vwProductRepo.findByStatusInAndNameLike(statuses, "%" + search + "%");
    }



    public List<VwProductEntity> showSellerProducts(String username) {
        return vwProductRepo.findByUsername(username);
    }

    public List<VwProductEntity> showMyProducts(String username)
    {
        return vwProductRepo.findByUsername(username);
    }

    public ProductEntity show(int productId) {
        return productRepo.findByProductId(productId);
    }

    public void store(ProductDto pDto, String username) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ProductEntity pEntity = new ProductEntity();
        int userId = userService.getUserIdByUsername(username);
        pEntity.setUserId(userId);
        if(pDto.getImage().getSize() > 0){
            pEntity.setImage(cloudinary.uploader().upload(pDto.getImage().getBytes(), ObjectUtils.emptyMap()).get("url").toString());
        }
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
        if(pDto.getImage().getSize() > 0){
            pEntity.setImage(cloudinary.uploader().upload(pDto.getImage().getBytes(), ObjectUtils.emptyMap()).get("url").toString());
        }
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

    public boolean validationStoreProduct(String username){
        List<String> statuses = new ArrayList<>();
        statuses.add("active");
        statuses.add("bidded");
        List<VwProductEntity> product  = vwProductRepo.findByUsernameAndStatusIn(username, statuses);
        return product.size() < 4;
    }

    public boolean setProductStatus(Integer productId, String status) {
        ProductEntity product = productRepo.findByProductId(productId);
        product.setStatus(status);
        productRepo.save(product);
        return true;
    }
}
