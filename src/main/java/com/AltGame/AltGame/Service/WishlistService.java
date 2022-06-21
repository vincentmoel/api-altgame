package com.AltGame.AltGame.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AltGame.AltGame.Dto.WishlistDto;
import com.AltGame.AltGame.Entity.WishlistEntity;
import com.AltGame.AltGame.Repository.WishlistRepo;

@Service
public class WishlistService {
	@Autowired
	WishlistRepo wishlistRepo;

	public List<WishlistEntity> index(int userId) {
		return wishlistRepo.findByUserId(userId);
	}

	public WishlistEntity store(WishlistDto wishlistDto) {
		WishlistEntity wEntity = new WishlistEntity();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		wEntity.setWishlistId(wishlistDto.getWishlistId());
		wEntity.setUserId(wishlistDto.getUserId());
		wEntity.setProductId(wishlistDto.getProductId());
		wEntity.setCreatedAt(timestamp);
		wEntity.setUpdatedAt(timestamp);

		return wishlistRepo.save(wEntity);
	}

	public List<WishlistEntity> destroy(int wishlistId) {
		WishlistEntity wEntity = wishlistRepo.findByWishlistId(wishlistId);
		if (wEntity != null)
			wishlistRepo.deleteById(wishlistId);
		return wishlistRepo.findAll();
	}

}
