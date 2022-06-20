package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.BidDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bids")
public class BidController {
    @Autowired
    BidService bidService;

    // Get All Data From Table
    @GetMapping(path="/index")
    public ResponseDto index()
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        Map<String, List<BidEntity>> mapBids = new HashMap<>();
        mapBids.put("bids",bidService.index((String) authUser.getPrincipal()));
        return new ResponseDto("200","Success Index Bid",mapBids);
    }

    // Get One Data From Table
    @GetMapping(path="/show/{bidId}")
    public ResponseDto show(@PathVariable int bidId)
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        BidEntity bid = bidService.show((String) authUser.getPrincipal(), bidId);

        Map<String, BidEntity> mapBid = new HashMap<>();
        mapBid.put("bids",bid);

        return new ResponseDto("200","Success Get Bid",mapBid);
    }

    // Save Data To Table
    @PostMapping(path="/store")
    public ResponseDto store(@RequestBody BidDto bidDto)
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        BidEntity bid = bidService.store((String) authUser.getPrincipal(), bidDto);

        return new ResponseDto("200","Success Store Bid", bid);
    }

    // Update Data To Table
    @PostMapping(path="/update/{bidId}")
    public ResponseDto update(@PathVariable int bidId, @RequestBody BidDto bidDto)
    {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        BidEntity bid = bidService.update((String) authUser.getPrincipal(), bidId, bidDto);

        return new ResponseDto("200","Success Update Bid", bid);
    }

    // Delete Data From Table
    @PostMapping(path="/destroy/{bidId}")
    public ResponseDto destroy(@PathVariable int bidId)
    {
        ResponseDto responseDto;
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        boolean status = bidService.destroy((String) authUser.getPrincipal(), bidId);

        if(status)
        {
            responseDto = new ResponseDto("200","Success Destroy Bid");
        }
        else
        {
            responseDto = new ResponseDto("204", "Failed Destroy Bid");
        }
        return responseDto;
    }


}
