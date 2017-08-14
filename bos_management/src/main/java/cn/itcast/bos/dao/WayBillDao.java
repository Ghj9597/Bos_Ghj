package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 犹良 on 2017/8/8 0008.
 */
public interface WayBillDao extends JpaRepository<WayBill,Integer>{


    WayBill findByWayBillNum(String wayBillNum);
}
