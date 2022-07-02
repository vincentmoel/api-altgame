package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface WishlistRepo extends JpaRepository<WishlistEntity, Integer> {
	WishlistEntity findByWishlistIdAndUserId(int wishlistId, int userId);

	List<WishlistEntity> findByUserId(int userId);

    Object findByProductIdAndUserId(Integer productId, Integer userId);
}
