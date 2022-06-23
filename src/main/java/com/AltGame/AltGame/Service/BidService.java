package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.BidDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Repository.BidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidService {
    @Autowired
    BidRepo bidRepo;

    @Autowired
    UserService userService;

    public List<BidEntity> index(String username){
        Integer userId = userService.getUserIdByUsername(username);

        return bidRepo.findByUserId(userId);
    }

    public BidEntity show(String username, Integer bidId)
    {
        Integer userId = userService.getUserIdByUsername(username);

        return bidRepo.findByBidIdAndUserId(bidId, userId);
    }

    public BidEntity store(String username, BidDto bidDto)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BidEntity bid = new BidEntity();

        bid.setUserId(userService.getUserIdByUsername(username));
        bid.setProductId(bidDto.getProductId());
        bid.setPrice(bidDto.getPrice());
        bid.setStatus("active");
        bid.setCreatedAt(timestamp);
        bid.setUpdatedAt(timestamp);

        return bidRepo.save(bid);
    }

    public BidEntity update(String username, Integer bidId, BidDto bidDto)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Integer userId = userService.getUserIdByUsername(username);

        BidEntity bid = bidRepo.findByBidIdAndUserId(bidId, userId);
        bid.setPrice(bidDto.getPrice());
        bid.setUpdatedAt(timestamp);

        return bidRepo.save(bid);
    }

    public boolean destroy(String username, Integer bidId)
    {
        Integer userId = userService.getUserIdByUsername(username);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BidEntity bid = bidRepo.findByBidIdAndUserId(bidId, userId);
        bid.setDeletedAt(timestamp);

        bidRepo.save(bid);

        return true;
    }
}
