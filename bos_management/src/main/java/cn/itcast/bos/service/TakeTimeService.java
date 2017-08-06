package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.TakeTime;

import java.util.List;

/**
 * Created by 犹良 on 2017/7/31 0031.
 */
public interface TakeTimeService {
    List<TakeTime> findAll();
}
