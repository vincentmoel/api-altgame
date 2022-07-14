package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Dto.BidDto;
import com.AltGame.AltGame.Dto.ResponseBid;
import com.AltGame.AltGame.Dto.UserInformationDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Entity.ProductEntity;
import com.AltGame.AltGame.Repository.BidRepo;
import com.AltGame.AltGame.Repository.InvoiceRepo;
import com.AltGame.AltGame.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BidService {
    @Autowired
    BidRepo bidRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    UserService userService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ProductService productService;

    @Autowired
    NotificationService notificationService;

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

    public void store(String username, BidDto bidDto)
    {
        BidEntity bid = new BidEntity();

        bid.setUserId(userService.getUserIdByUsername(username));
        bid.setProductId(bidDto.getProductId());
        bid.setPrice(bidDto.getPrice());
        bid.setStatus("active");
        bid.setCreatedAt(timestamp);
        bid.setUpdatedAt(timestamp);

        productService.setProductStatus(bidDto.getProductId(),"bidded");

        bidRepo.save(bid);
    }

    public void update(String username, Integer bidId, BidDto bidDto)
    {
        Integer userId = userService.getUserIdByUsername(username);

        BidEntity bid = bidRepo.findByBidIdAndUserId(bidId, userId);
        bid.setPrice(bidDto.getPrice());
        bid.setUpdatedAt(timestamp);

        bidRepo.save(bid);
    }

    public boolean destroy(String username, Integer bidId)
    {
        Integer userId = userService.getUserIdByUsername(username);

        BidEntity bid = bidRepo.findByBidIdAndUserId(bidId, userId);

        bidRepo.deleteById(bid.getBidId());

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

    public List<Object> mergeResponse(List<BidEntity> bidEntities)
    {
        List<Object> bidsProduct = new ArrayList<>();
        for (BidEntity bidEntity : bidEntities)
        {
            ResponseBid responseBid = new ResponseBid();
            String username = userService.getUsernameByUserId(bidEntity.getUserId());
            UserInformationDto userInformation = userService.getUserInfoByUsername(username);
            responseBid.setBidId(bidEntity.getBidId());
            responseBid.setProductId(bidEntity.getProductId());
            responseBid.setPrice(bidEntity.getPrice());
            responseBid.setStatus(bidEntity.getStatus());
            responseBid.setCreatedAt(bidEntity.getCreatedAt());
            responseBid.setAcceptedAt(bidEntity.getAcceptedAt());
            responseBid.setUser(userInformation);

            bidsProduct.add(responseBid);

        }

        return bidsProduct;
    }

    public boolean acceptBidBuyer(Integer bidId, String username)
    {
        Integer userId = userService.getUserIdByUsername(username);

        // Only Product's Owner can Accept
        BidEntity bid = bidRepo.findByBidId(bidId);
        ProductEntity product = productRepo.findByProductId(bid.getProductId());
        if(Objects.equals(product.getUserId(), userId))
        {
            setStatusToAccepted(bidId);
            setAllStatusToDeclined(bid.getProductId());
            invoiceService.store(bidId);
            productService.setProductStatus(bid.getProductId(), "waiting");
            notificationService.store(invoiceRepo.lastInvoiceId());

            return true;
        }
        return false;

    }

    public BidEntity setStatusToAccepted(Integer bidId)
    {
        BidEntity bid = bidRepo.findByBidId(bidId);
        bid.setStatus("accepted");
        bid.setAcceptedAt(timestamp);
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
