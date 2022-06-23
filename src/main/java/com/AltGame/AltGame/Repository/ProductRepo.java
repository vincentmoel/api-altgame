package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepo extends JpaRepository<ProductEntity, Integer> {

	List<ProductEntity> findByUserId(int userId);

	ProductEntity findByProductId(int productId);

	ProductEntity findByProductIdAndUserId(int productId, int userId);
  
  ProductEntity findByProductId(Integer productId);

}
