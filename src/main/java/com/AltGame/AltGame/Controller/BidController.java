package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.BidDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Service.BidService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/bids")
public class BidController {
    @Autowired
    BidService bidService;

    @Autowired
    UserService userService;

    // Get All Data From Table
    // history buyer
    @GetMapping(path="/index")
    public ResponseEntity<?> index()
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> mapBids = new HashMap<>();
        mapBids.put("bids",bidService.index((String) authUser.getPrincipal()));
        if(bidService.index((String) authUser.getPrincipal()).isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("202","Success Index Bid",mapBids), HttpStatus.ACCEPTED);
    }

    // Get One Data From Table
    // show buyer
    @GetMapping(path="/show/{bidId}")
    public ResponseEntity<?> show(@PathVariable int bidId)
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        BidEntity bid = bidService.show((String) authUser.getPrincipal(), bidId);

        Map<String, Object> mapBid = new HashMap<>();
        mapBid.put("bids",bid);
        if(Objects.isNull(bid)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("202","Success Get Bid",mapBid), HttpStatus.ACCEPTED);
    }

    // Save Data To Table
    // buyer
    @PostMapping(path="/store")
    public ResponseEntity<?> store(@RequestBody BidDto bidDto)
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        bidService.store((String) authUser.getPrincipal(), bidDto);

        return new ResponseEntity<>(new ResponseDto("201","Success Store Bid"), HttpStatus.CREATED);
    }

    // Update Data To Table
    // buyer
    @PostMapping(path="/update/{bidId}")
    public ResponseEntity<?> update(@PathVariable int bidId, @RequestBody BidDto bidDto)
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        bidService.update((String) authUser.getPrincipal(), bidId, bidDto);

        return new ResponseEntity<>(new ResponseDto("202","Success Update Bid"), HttpStatus.ACCEPTED) ;
    }

    // Delete Data From Table
    // buyer
    @PostMapping(path="/destroy/{bidId}")
    public ResponseEntity<?> destroy(@PathVariable int bidId)
    {
        ResponseDto responseDto;
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        boolean status = bidService.destroy((String) authUser.getPrincipal(), bidId);

        if(status)
        {
            return new ResponseEntity<>(new ResponseDto("202","Success Destroy Bid"), HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>(new ResponseDto("400", "Failed Destroy Bid"), HttpStatus.BAD_REQUEST);
        }
    }

    // Can be access by Product Owner only
    // Show all bids from product
    @GetMapping(path="/all-bids-product/{productId}")
    public ResponseEntity<?> getAllBidsOnProduct(@PathVariable Integer productId)
    {
        List<BidEntity> bids = bidService.getAllBidsOnProduct(productId, userService.authentication().getPrincipal().toString());
        Map<String, Object> response = new HashMap<>();
        response.put("Bids", bids);
        if(bids.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ResponseDto("302","Success Get All Bid On Product", response), HttpStatus.FOUND);
    }

    // Can be access by Product Owner only
    @PostMapping(path="/accept-bid-buyer/{bidId}")
    public ResponseEntity<?> acceptBidBuyer(@PathVariable Integer bidId)
    {
        ResponseDto responseDto;
        boolean status = bidService.acceptBidBuyer(bidId, userService.authentication().getPrincipal().toString());

        if(status)
        {
            return new ResponseEntity<>(new ResponseDto("202", "Success Accept Bid Buyer"), HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>(new ResponseDto("401", "Failed Accept Bid Buyer"), HttpStatus.BAD_REQUEST);
        }
    }


}
