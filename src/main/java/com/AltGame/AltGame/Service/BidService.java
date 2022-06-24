package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.BidDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Repository.BidRepo;
import com.AltGame.AltGame.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class BidService {
    @Autowired
    BidRepo bidRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserService userService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ProductService productService;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());


    public List<BidEntity> index(String username){
        Integer userId = userService.getUserIdByUsername(username);

        return bidRepo.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public BidEntity show(String username, Integer bidId)
    {
        Integer userId = userService.getUserIdByUsername(username);

        return bidRepo.findByBidIdAndUserId(bidId, userId);
    }

    public BidEntity store(String username, BidDto bidDto)
    {
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
        Integer userId = userService.getUserIdByUsername(username);

        BidEntity bid = bidRepo.findByBidIdAndUserId(bidId, userId);
        bid.setPrice(bidDto.getPrice());
        bid.setUpdatedAt(timestamp);

        return bidRepo.save(bid);
    }

    public boolean destroy(String username, Integer bidId)
    {
        Integer userId = userService.getUserIdByUsername(username);

        BidEntity bid = bidRepo.findByBidIdAndUserId(bidId, userId);
        bid.setDeletedAt(timestamp);

        bidRepo.save(bid);

        return true;
    }

    public List<BidEntity> getAllBidsOnProduct(Integer productId, String username)
    {
        List<BidEntity> arrBid;
        Integer userId = userService.getUserIdByUsername(username);

        // Only Product's Owner can Access
        ProductEntity product = productRepo.findByProductId(productId);
        if(Objects.equals(product.getUserId(), userId))
        {
            arrBid = bidRepo.findAllByProductIdOrderByCreatedAtDesc(productId);
            return arrBid;
        }
        return null;
    }

    public boolean acceptBidBuyer(Integer bidId, BidDto bidDto, String username)
    {
        Integer userId = userService.getUserIdByUsername(username);

        // Only Product's Owner can Accept
        ProductEntity product = productRepo.findByProductId(bidDto.getProductId());
        if(Objects.equals(product.getUserId(), userId))
        {
            setStatusToAccepted(bidId);
            setAllStatusToDeclined(bidDto.getProductId());
            invoiceService.store(bidId);
            productService.setProductStatus(bidDto.getProductId(), "sold");
            return true;
        }
        return false;

    }

    public BidEntity setStatusToAccepted(Integer bidId)
    {
        BidEntity bid = bidRepo.findByBidId(bidId);
        bid.setStatus("accepted");
        bid.setUpdatedAt(timestamp);
        return bid;
    }

    public boolean setAllStatusToDeclined(Integer productId)
    {
        List<BidEntity> bids = bidRepo.findAllByProductIdAndStatus(productId, "active");
        for(BidEntity bid : bids) {
            bid.setStatus("declined");
            bid.setUpdatedAt(timestamp);
        }
        bidRepo.saveAll(bids);
        return true;

    }
}
