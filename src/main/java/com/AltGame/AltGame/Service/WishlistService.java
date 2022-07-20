package com.AltGame.AltGame.Service;

import java.sql.Timestamp;
import java.util.*;

import com.AltGame.AltGame.Dto.ResponseWhishlist;
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
	@Autowired
	ProductService productService;

	public List<ResponseWhishlist> index(String username)
	{
		int userId = userService.getUserIdByUsername(username);
		List<ResponseWhishlist> responseWhishlists = new ArrayList<>();
		for(WishlistEntity i : wishlistRepo.findByUserId(userId)){
			ResponseWhishlist temp = new ResponseWhishlist();
			temp.setWishlistId(i.getWishlistId());
			temp.setUser(userService.getUserInfoByUserId(userId));
			temp.setProduct(productService.show(i.getProductId()));
			temp.setCreatedAt(i.getCreatedAt());
			temp.setUpdatedAt(i.getUpdatedAt());
			responseWhishlists.add(temp);
		}
		return responseWhishlists;
	}

	public WishlistEntity store(WishlistDto wishlistDto, String username)
	{
		WishlistEntity wishlistEntity = new WishlistEntity();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		int userId = userService.getUserIdByUsername(username);

		wishlistEntity.setWishlistId(wishlistDto.getWishlistId());
		wishlistEntity.setUserId(userId);
		wishlistEntity.setProductId(wishlistDto.getProductId());
		wishlistEntity.setCreatedAt(timestamp);
		wishlistEntity.setUpdatedAt(timestamp);

		return wishlistRepo.save(wishlistEntity);
	}

	public boolean destroy(WishlistDto wishlistDto, String username)
	{
		int userId = userService.getUserIdByUsername(username);
		WishlistEntity wishlistEntity = wishlistRepo.findByWishlistIdAndUserId(wishlistDto.getWishlistId(), userId);
		if (wishlistEntity != null)
		{
			wishlistRepo.deleteById(wishlistDto.getWishlistId());
			return true;
		}
		return false;

	}

    public Map<String, Boolean> isProductInWishlist(Integer productId, Integer userId) {
		Map<String, Boolean> response = new HashMap<>();
		if(Objects.isNull(wishlistRepo.findByProductIdAndUserId(productId,userId))){
			response.put("in_wishlist",false);
		}else{
			response.put("in_wishlist",true);
		}
		return response;
    }
}
