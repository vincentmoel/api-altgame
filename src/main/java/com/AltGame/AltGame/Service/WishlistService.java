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
	@Autowired
	UserService userService;

	public List<WishlistEntity> index(String username) {
		int userId = userService.getUserIdByUsername(username);
		return wishlistRepo.findByUserId(userId);
	}

	public WishlistEntity store(WishlistDto wishlistDto, String username) {

		WishlistEntity wEntity = new WishlistEntity();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		int userId = userService.getUserIdByUsername(username);

		wEntity.setWishlistId(wishlistDto.getWishlistId());
		wEntity.setUserId(userId);
		wEntity.setProductId(wishlistDto.getProductId());
		wEntity.setCreatedAt(timestamp);
		wEntity.setUpdatedAt(timestamp);

		return wishlistRepo.save(wEntity);
	}

	public boolean destroy(WishlistDto wishlistDto, String username) {
		int userId = userService.getUserIdByUsername(username);
		WishlistEntity wEntity = wishlistRepo.findByWishlistIdAndUserId(wishlistDto.getWishlistId(), userId);
		if (wEntity != null)
		{
			wishlistRepo.deleteById(wishlistDto.getWishlistId());
			return true;
		}

		return false;

	}

}
