package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.take_delivery.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by 犹良 on 2017/8/8 0008.
 */
public interface WayBillService {

    void save(WayBill t);

    Page<WayBill> findAll(Pageable pageable,WayBill wayBill);


    WayBill findByWayBillNum(String wayBillNum);
    void synchro();
}
