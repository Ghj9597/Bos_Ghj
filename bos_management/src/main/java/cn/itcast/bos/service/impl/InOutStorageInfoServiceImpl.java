package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.InOutStorageInfoDao;
import cn.itcast.bos.dao.TransitInfoDao;
import cn.itcast.bos.domain.base.transit.InOutStorageInfo;
import cn.itcast.bos.domain.base.transit.TransitInfo;
import cn.itcast.bos.service.InOutStorageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 犹良 on 2017/8/15 0015.
 */
@Service()
@Transactional
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {
    //注入TransitIonfoDao
    @Autowired
    private TransitInfoDao transitInfoDao;
    //创建InOutStorageInfoServiceDao;
    @Autowired
    private InOutStorageInfoDao inOutStorageInfoDao;
    @Override
    public void save(String transitinfoid, InOutStorageInfo t) {
    //先调用InOutStorageInfoDao保存;
        inOutStorageInfoDao.save(t);
        //调用TransiTInfoDao查询出TranSitInfo;
        int i = Integer.parseInt(transitinfoid);
        //由于InOutStorageInfoDao中有一行索引列,由于此索引列需要TransitInfo关联,所以要使用TrasnSitInfo关联;
        TransitInfo transitInfo = transitInfoDao.findOne(i);
        transitInfo.getInOutStorageInfos().add(t);
        //保存成功后修改TransitInfo的状态;
        if (t.getOperation().equals("到达网点")){
            //判断页面提交的是否为到达网点;
            //是的话将TransitInfo的值更改为到达网点,并且将地址值存储进去;
            transitInfo.setStatus("到达网点");
            transitInfo.setOutletAddress(t.getAddress());
        }
        //如果不是到达网点那就不需要修改了;
    }
}
