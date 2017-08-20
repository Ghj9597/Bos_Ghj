package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.SignInfoDao;
import cn.itcast.bos.dao.TransitInfoDao;
import cn.itcast.bos.dao.es.WayBillRepository;
import cn.itcast.bos.domain.base.transit.SignInfo;
import cn.itcast.bos.domain.base.transit.TransitInfo;
import cn.itcast.bos.service.SignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 犹良 on 2017/8/18 0018.
 */
@Service
@Transactional
public class SignInfoServiceImpl implements SignInfoService {
    @Autowired
    private SignInfoDao signInfoDao;
    @Autowired
    private TransitInfoDao transitInfoDao;
    //由于我们更改了运单状态所以要注入ElasticSearch
    @Autowired
    private WayBillRepository wayBillRepository;
    @Override
    public void save(String transitinfoid, SignInfo t) {
        signInfoDao.save(t);
        int i = Integer.parseInt(transitinfoid);
        TransitInfo transitInfo = transitInfoDao.findOne(i);
        //关联;
        transitInfo.setSignInfo(t);
        //判断是正常状态还是异常签收状态;
        if(t.getSignType().equals("正常")){
            //正常时;
            transitInfo.setStatus("正常签收");
            transitInfo.getWayBill().setSignStatus(3);
        }else{
            transitInfo.setStatus("异常");
            transitInfo.getWayBill().setSignStatus(4);
        }
        //更新保存后的订单;
        wayBillRepository.save(transitInfo.getWayBill());
    }
}
