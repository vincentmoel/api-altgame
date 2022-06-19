package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {
    @Autowired
    BidService bidService;

    // Get All Data From Table
    @GetMapping(path="/index")
    public ResponseDto index()
    {
        ResponseDto responseDto = new ResponseDto("200","Success Store User",bidService.index());
        return responseDto;
    }

    // Get One Data From Table
    @GetMapping(path="/show/{idBid}")
    public void show(@PathVariable int idBid)
    {

    }

    // Save Data To Table
    @PostMapping(path="/store")
    public void store()
    {

    }

    // Update Data To Table
    @PostMapping(path="/update/{idBid}")
    public void update(@PathVariable int idBid)
    {

    }

    // Delete Data From Table
    @PostMapping(path="/destroy/{idBid}")
    public void destroy(@PathVariable int idBid)
    {

    }
}
