package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.TransitInfoDao;
import cn.itcast.bos.dao.WayBillDao;
import cn.itcast.bos.domain.base.take_delivery.WayBill;
import cn.itcast.bos.domain.base.transit.TransitInfo;
import cn.itcast.bos.service.TransitInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 犹良 on 2017/8/14 0014.
 */
@Service
@Transactional
public class TransitInfoServiceImpl  implements TransitInfoService {
    @Autowired
    private WayBillDao wayBillDao;
    @Autowired
    private TransitInfoDao transitInfoDao;

    //建立保存方法注入Dao层;
    @Override
    public void createTransit(String wayBillsId) {
        //判断如果传入的id不为空或者空字符串时;
        if (StringUtils.isNotBlank(wayBillsId)){
            String[] split = wayBillsId.split(",");
            for (String id:split) {
                int i = Integer.parseInt(id);
                //查询出每一个wayBill运单
                WayBill wayBill = wayBillDao.findOne(i);
                //判断运单的运单状态为待发货
                if(wayBill.getSignStatus()==1){
                    TransitInfo transitInfo = new TransitInfo();
                    transitInfo.setWayBill(wayBill);
                    transitInfo.setStatus("出入库中转");
                    transitInfoDao.save(transitInfo);
                    //最后我们将wayBill的运单状态改为派送中
                    wayBill.setSignStatus(2);
                }
            }

        }
    }

    @Override
    public Page<TransitInfo> findAll(Pageable pageable) {
        Page<TransitInfo> all = transitInfoDao.findAll(pageable);
        return all;
    }
}
