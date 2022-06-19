package com.AltGame.AltGame.Service;

import com.AltGame.AltGame.Entity.BidEntity;
import com.AltGame.AltGame.Repository.BidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    BidRepo bidRepo;

    public List<BidEntity> index(){
        return bidRepo.findAll();
    }
}
