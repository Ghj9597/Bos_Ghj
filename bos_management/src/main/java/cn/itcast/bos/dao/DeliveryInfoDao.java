package cn.itcast.bos.dao;


import cn.itcast.bos.domain.base.transit.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
public interface DeliveryInfoDao extends JpaRepository<DeliveryInfo,Integer> {
}
