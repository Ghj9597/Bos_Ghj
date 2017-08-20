package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.transit.DeliveryInfo;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
public interface DeliveryInfoService {
    void save(String transitinfoid, DeliveryInfo t);
}
