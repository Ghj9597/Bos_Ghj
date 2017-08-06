package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by 犹良 on 2017/8/3 0003.
 */
public interface PromotionDao extends JpaRepository<Promotion,Integer>{
    @Query("update Promotion set status='2' where endDate< ?1 and status='1'")
    @Modifying
    void updateStatus(Date data);
}
