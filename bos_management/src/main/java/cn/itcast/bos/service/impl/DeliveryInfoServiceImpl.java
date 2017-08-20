package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.DeliveryInfoDao;
import cn.itcast.bos.dao.TransitInfoDao;
import cn.itcast.bos.domain.base.transit.DeliveryInfo;
import cn.itcast.bos.domain.base.transit.TransitInfo;
import cn.itcast.bos.service.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
@Service
@Transactional
public class DeliveryInfoServiceImpl implements DeliveryInfoService {
    @Autowired
    private DeliveryInfoDao deliveryInfoDao;
    @Autowired
    private TransitInfoDao transitInfoDao;
    @Override
    public void save(String transitinfoid, DeliveryInfo t) {
        deliveryInfoDao.save(t);
        int i = Integer.parseInt(transitinfoid);
        TransitInfo transitInfo = transitInfoDao.findOne(i);
        transitInfo.setDeliveryInfo(t);
        transitInfo.setStatus("开始配送");
    }
}
