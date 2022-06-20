package com.AltGame.AltGame.Repository;

import com.AltGame.AltGame.Entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BidRepo extends JpaRepository<BidEntity, Integer> {
    BidEntity findByBidIdAndUserId(Integer bidId, Integer userId);
    List<BidEntity> findByUserId(Integer userId);
}
