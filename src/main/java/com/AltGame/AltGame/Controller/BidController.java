package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.BidDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Dto.UserInformationDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Service.BidService;
import com.AltGame.AltGame.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
        List<BidEntity> bids = bidService.index(userService.authentication().getName());
        if(bidService.index(userService.authentication().getName()).isEmpty()){
           return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Bid Data Not Found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Index Bid", bids), HttpStatus.OK);
    }

    // Get One Data From Table
    // show buyer
    @GetMapping(path="/show/{bidId}")
    public ResponseEntity<?> show(@PathVariable int bidId)
    {
        BidEntity bid = bidService.show(userService.authentication().getName(), bidId);

        if(Objects.isNull(bid)){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404", "Bid Data Not Found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Get Bid", bid), HttpStatus.OK);
    }

    // Save Data To Table
    // buyer
    @PostMapping(path="/store")
    public ResponseEntity<?> store(@RequestBody BidDto bidDto)
    {
        bidService.store(userService.authentication().getName(), bidDto);
        return new ResponseEntity<>(new ResponseDto().responseBuilder("201","Success Store Bid"), HttpStatus.CREATED);
    }

    // Update Data To Table
    // buyer
    @PostMapping(path="/update/{bidId}")
    public ResponseEntity<?> update(@PathVariable int bidId, @RequestBody BidDto bidDto)
    {
        bidService.update(userService.authentication().getName(), bidId, bidDto);

        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Update Bid"), HttpStatus.OK) ;
    }

    // Delete Data From Table
    // buyer
    @PostMapping(path="/destroy/{bidId}")
    public ResponseEntity<?> destroy(@PathVariable int bidId)
    {
        boolean status = bidService.destroy(userService.authentication().getName(), bidId);

        if(status)
        {
            return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Destroy Bid"), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Failed Destroy Bid"), HttpStatus.BAD_REQUEST);
        }
    }

    // Can be access by Product Owner only
    // Show all bids from product
    @GetMapping(path="/all-bids-product/{productId}")
    public ResponseEntity<?> getAllBidsOnProduct(@PathVariable Integer productId)
    {

        List<BidEntity> bids = bidService.getAllBidsOnProduct(productId, userService.authentication().getName());

        if(bids.isEmpty()){
            return new ResponseEntity<>(new ResponseDto().responseBuilder("404","Data Bids On Product Not Found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto().responseBuilder("200","Success Get All Bid On Product", bidService.mergeResponse(bids)), HttpStatus.OK);
    }

    // Can be access by Product Owner only
    @PostMapping(path="/accept-bid-buyer/{bidId}")
    public ResponseEntity<?> acceptBidBuyer(@PathVariable Integer bidId)
    {
        boolean status = bidService.acceptBidBuyer(bidId, userService.authentication().getName());

        if(status)
        {
            return new ResponseEntity<>(new ResponseDto().responseBuilder("200", "Success Accept Bid Buyer"), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ResponseDto().responseBuilder("400", "Failed Accept Bid Buyer"), HttpStatus.BAD_REQUEST);
        }
    }


}
