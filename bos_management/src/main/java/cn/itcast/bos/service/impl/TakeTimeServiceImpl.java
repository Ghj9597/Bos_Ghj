package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.TakeTimeDao;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by 犹良 on 2017/7/31 0031.
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeDao takeTimeDao;

    public List<TakeTime> findAll() {
        List<TakeTime> all = takeTimeDao.findAll();
        return all;
    }
}
