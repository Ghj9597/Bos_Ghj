package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 犹良 on 2017/8/7 0007.
 */
public interface WorkBillDao extends JpaRepository<WorkBill,Integer>{
}
